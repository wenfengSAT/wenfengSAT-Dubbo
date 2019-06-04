package com.dubbo.api.service.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.dubbo.api.constant.ErrorCode;
import com.dubbo.api.constant.OrderStatus;
import com.dubbo.api.constant.Result;
import com.dubbo.api.dao.MasterOrderMapper;

/**
 * 
 * @Description： 支付宝回调接口
 * 
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月31日下午8:58:42]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Service(version = "1.0.0")
public class AliPayCallbackServiceImpl implements AliPayCallbackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AliPayCallbackServiceImpl.class);
	
	@Value("${alipay_private_key}")
	private String privateKey;
	@Value("${alipay_public_key}")
	private String alipayPublicKey;
	@Value("${alipay_app_id}")
	private String alipayAppId;
	@Value("${alipay_gateway}")
	private String serviceUrl;
	@Value("${alipay_charset}")
	private String charset;
	@Value("${alipay_sign_type}")
	private String signType;
	@Value("${alipay_format}")
	private String format;
	@Value("${alipay_notify_url}")
	private String notifyUrl;
	@Value("${alipay_seller_id}")
	private String sellerid;
	@Value("${alipay_timeoutexpress}")
	private String timeoutExpress;
	
	@Autowired
	private MasterOrderMapper masterOrderMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * * 支付宝支付回调
	 * 
     * 第一步:验证签名,签名通过后进行第二步
     * 第二步:按步骤进行验证
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     * 
	 */
	@Override
	public Result alipayNotify(Map<String, String> params) {
		LOGGER.info("alipayNotify params:{}", JSON.toJSONString(params));
		String outTradeNo = params.get("out_trade_no");// 商户订单号
		String totalAmount = params.get("total_amount");// 总金额
		String appId = params.get("app_id");
		String sellerId = params.get("seller_id");
		String orderIdKey = "alipay_" + outTradeNo;

		if (redisTemplate.hasKey(orderIdKey)) {
			LOGGER.info("该订单号已执行, orderId {}:", outTradeNo);
			return Result.error(ErrorCode.ALIPAY_ORDER_IS_EXECUTED);
		} else {// 12小时主键过期
			redisTemplate.opsForValue().set(orderIdKey, outTradeNo, 12 * 60 * 60);
		}
		// 一、支付宝验签
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);
		} catch (AlipayApiException e) {
			LOGGER.error("signVerified is failure");
			return Result.error(ErrorCode.ALIPAY_SIGN_VERIFIED_FAIL);
		}
		LOGGER.info("支付宝验签结果:" + signVerified);
		if (!signVerified) {
			return Result.error(ErrorCode.ALIPAY_SIGN_VERIFIED_FAIL);
		}
		// 1、订单校验
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORDER_ID", outTradeNo);
		List<Map<String, Object>> orderList = masterOrderMapper.selectOrder(param);
		if (orderList == null || orderList.size() < 1) {
			LOGGER.error("alipay order not exist! orderId:{}", outTradeNo);
			return Result.error(ErrorCode.ORDER_NOT_EXIST);
		}
		Map<String, Object> order = orderList.get(0);
		// 2、金额校验
		Object orderTotal = order.get("ORDER_TOTAL");
		if (Objects.isNull(orderTotal)) {
			LOGGER.error("数据库订单金额为空! orderId:{}", outTradeNo);
			return Result.error(ErrorCode.AMOUNT_CHECK_ERROR);
		}
		int compareValue = new BigDecimal(String.valueOf(totalAmount))
				.compareTo(new BigDecimal(String.valueOf(orderTotal)));
		if (0 != compareValue) {
			LOGGER.error("alipay order amount not equal! orderId:{},totalAmount:{},orderTotal:{}", outTradeNo,
					totalAmount, orderTotal);
			return Result.error(ErrorCode.AMOUNT_CHECK_ERROR);
		}
		// 3、4、参数校验
		if (!alipayAppId.equals(appId)) {
			return Result.error(ErrorCode.ALIPAY_PARAMETER_APPID_CHECK_ERROR);
		}
		if (!sellerid.equals(sellerId)) {
			return Result.error(ErrorCode.ALIPAY_PARAMETER_SELLERID_CHECK_ERROR);
		}
		// 更新订单状态-->已支付
		param.put("PAY_STATUS", OrderStatus.PAID.getValue());
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
		int updateResult = masterOrderMapper.updateOrderStatus(param);
		if(updateResult < 1) {
			return false;
		}
		return true;
	}

}
