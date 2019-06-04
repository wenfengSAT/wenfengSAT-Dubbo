package com.dubbo.api.service.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.dubbo.api.constant.Const;
import com.dubbo.api.constant.ErrorCode;
import com.dubbo.api.constant.OrderStatus;
import com.dubbo.api.constant.Result;
import com.dubbo.api.dao.MasterOrderMapper;
import com.dubbo.api.utils.SignUtil;

/**
 * 
 * @Description： 支付宝回调接口
 * 
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年6月4日上午11:27:55]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class WxPayCallbackServiceImpl implements WxPayCallbackService {

	@Value("${weixin_mch_id}")
	private String weixinMchId;
	@Value("${weixin_appId}")
	private String weixinAppId;
	@Value("${weixin_key}")
	private String weixinKey;
	@Value("${weixin_notifyUrl}")
	private String weixinNotifyUrl;
	@Value("${weixin_payOrderUrl}")
	private String payOrderUrl;
	@Value("${weixin_trade_type}")
	private String tradeType;

	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayCallbackServiceImpl.class);

	@Autowired
	private MasterOrderMapper masterOrderMapper;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * * 微信支付回调
	 * 
	 * 第一步:验证签名,签名通过后进行
	 * 第二步:按步骤进行验证 
	 * 1、验证return_code是否为SUCCESS 
	 * 2、判断订单是否存在 
	 * 3、验证金额
	 */
	@Override
	public Result wxpayNotify(Map<String, Object> params) {
		LOGGER.info("wxpayNotify params:{}", JSON.toJSONString(params));
		String resultSign = (String) params.get("sign");
		if (StringUtils.isBlank(resultSign)) {
			LOGGER.error("微信回调验签参数为空！");
			return Result.error(ErrorCode.WEIXIN_SIGN_VERIFIED_FAIL);
		}
		String sign = SignUtil.sign(params, weixinKey);
		if (!resultSign.equals(sign)) {
			LOGGER.error("微信回调验签失败！resultSign:{},sign:{}", resultSign, sign);
			return Result.error(ErrorCode.WEIXIN_SIGN_VERIFIED_FAIL);
		}
		String returnCode = (String) params.get("return_code");
		if (!Const.SUCCESS.equals(returnCode)) {
			LOGGER.error("微信回调return_code不为SUCCESS。returnCode:{}", returnCode);
			return Result.error(ErrorCode.WEIXIN_RETURN_CODE_FAIL);
		}
		String outTradeNo = (String) params.get("out_trade_no");
		String orderIdKey = "alipay_" + outTradeNo;
		if (redisTemplate.hasKey(orderIdKey)) {
			LOGGER.info("该订单号已执行, orderId {}:", outTradeNo);
			return Result.error(ErrorCode.WEIXIN_ORDER_IS_EXECUTED);
		} else {// 12小时主键过期
			redisTemplate.opsForValue().set(orderIdKey, outTradeNo, 12 * 60 * 60);
		}
		if (StringUtils.isBlank(outTradeNo)) {
			LOGGER.error("微信回调参数out_trade_no为空。");
			return Result.error(ErrorCode.WEIXIN_RETURN_PARAM_ERROR);
		}
		String orderId = outTradeNo.substring(0, outTradeNo.indexOf("_"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORDER_ID", orderId);
		List<Map<String, Object>> orderList = masterOrderMapper.selectOrder(param);
		if (orderList == null || orderList.size() < 1) {
			LOGGER.error("alipay order not exist! orderId:{}", outTradeNo);
			return Result.error(ErrorCode.ORDER_NOT_EXIST);
		}
		Map<String, Object> order = orderList.get(0);
		// 2、判断订单是否存在
		Object orderTotal = order.get("ORDER_TOTAL");
		if (Objects.isNull(orderTotal)) {
			LOGGER.error("数据库订单金额为空! orderId:{}", outTradeNo);
			return Result.error(ErrorCode.AMOUNT_CHECK_ERROR);
		}
		// 3、金额校验
		// 微信回调金额单位是分,需要转成元
		double cashFee = Double.parseDouble(String.valueOf(params.get("cash_fee"))) / 100;
		int compareValue = new BigDecimal(String.valueOf(cashFee))
				.compareTo(new BigDecimal(String.valueOf(orderTotal)));
		if (0 != compareValue) {
			LOGGER.error("wxpay order amount not equal! orderId:{},cashFee:{},orderTotal:{}", outTradeNo, cashFee,
					orderTotal);
			return Result.error(ErrorCode.AMOUNT_CHECK_ERROR);
		}
		boolean updateResult = updateOrderStatus(param);
		if (!updateResult) {
			LOGGER.error("更新订单状态失败。orderId:{}", outTradeNo);
			return Result.error(ErrorCode.UPDATE_PAY_STATUS_FAIL);
		}
		// TODO 支付成功之后业务处理...
		return Result.ok();
	}

	/**
	 * 更新订单状态
	 * 
	 * @param param
	 * @return
	 */
	public boolean updateOrderStatus(Map<String, Object> param) {
		// 更新订单状态-->已支付
		param.put("PAY_STATUS", OrderStatus.PAID.getValue());
		int updateResult = masterOrderMapper.updateOrderStatus(param);
		if (updateResult < 1) {
			return false;
		}
		return true;
	}
}
