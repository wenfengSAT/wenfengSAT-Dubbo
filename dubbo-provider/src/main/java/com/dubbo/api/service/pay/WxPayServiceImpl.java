package com.dubbo.api.service.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.dubbo.api.constant.Const;
import com.dubbo.api.constant.ErrorCode;
import com.dubbo.api.constant.PayChannel;
import com.dubbo.api.constant.Result;
import com.dubbo.api.dao.MasterOrderMapper;
import com.dubbo.api.service.util.wxpay.BeanUtil;
import com.dubbo.api.service.util.wxpay.IPUtil;
import com.dubbo.api.service.util.wxpay.SignUtil;
import com.dubbo.api.service.util.wxpay.UnifiedOrder;
import com.dubbo.api.service.util.wxpay.UnifiedOrderReturn;
import com.dubbo.api.service.util.wxpay.WeixinPayUtil;
import com.dubbo.api.service.util.wxpay.XmlUtil;

/**
 * 
 * @Description： 微信支付
 * 
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月31日上午9:06:42]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Service(version = "1.0.0")
public class WxPayServiceImpl implements WxPayService {

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

	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayServiceImpl.class);

	@Autowired
	private MasterOrderMapper masterOrderMapper;

	/**
	 * 获取微信支付二维码
	 */
	@Override
	public Result wxpayPrecreate(Map<String, String> param) {
		LOGGER.info("wxPrecreate params:{}", JSON.toJSONString(param));
		String orderId = param.get("ORDER_ID");
		String productName = param.get("PRODUCT_NAME");
		String price = param.get("PRICE");
		String productDesc = param.get("PRODUCT_DESC");
		String deviceInfo = param.get("DEVICEINFO");
		// 校验参数
		Result validateResult = precreateValidateParam(orderId, productName, price, productDesc);
		if (!Objects.isNull(validateResult)) {
			LOGGER.error("参数校验失败！{}", JSON.toJSONString(validateResult));
			return validateResult;
		}
		// 更新支付渠道为支付宝
		if (!updatePayChennel(orderId)) {
			LOGGER.error("更新支付渠道为微信失败！orderId:{}", orderId);
			return Result.error(ErrorCode.UPDATE_PAYCHENNEL_FAIL);
		}
		UnifiedOrder unifiedOrder = buildParam(orderId, productName, price, deviceInfo);
		Map<String, Object> paramMap = BeanUtil.object2Map(unifiedOrder); // 参数列表
		String sign = SignUtil.sign(paramMap, weixinKey);
		unifiedOrder.setSign(sign); // 计算sign
		paramMap.put("sign", sign);
		String xml = XmlUtil.toXml(paramMap);
		LOGGER.info("wxpayPrecreate xml param:{}", xml);
		String reslutXml = WeixinPayUtil.post4xml(payOrderUrl, xml.toString());
		Map<String, Object> resultMap = XmlUtil.parseXml(reslutXml);
		String resultSign = SignUtil.sign(resultMap, weixinKey);
		String returnSign = (String) resultMap.get("sign");
		if (returnSign == null || !returnSign.equals(resultSign)) {
			LOGGER.error("微信支付返回结果签名校验不通过,resultSign:{},returnSign:{}", resultSign, returnSign);
			return Result.error(ErrorCode.WEIXIN_SIGN_CHECK_FAIL);
		}
		UnifiedOrderReturn unifiedOrderReturn = BeanUtil.map2Object(UnifiedOrderReturn.class, resultMap);
		LOGGER.info("wxpayPrecreate result==orderId:{},payResult:{}", orderId, JSON.toJSONString(unifiedOrderReturn));
		// 判断是否请求成功
		if (!UnifiedOrderReturn.SUCCESS.equals(unifiedOrderReturn.getResultCode())
				|| !UnifiedOrderReturn.SUCCESS.equals(unifiedOrderReturn.getReturnCode())) {
			LOGGER.error("请求微信支付二维码失败!");
			return Result.error(ErrorCode.WEIXIN_REQUEST_QRCODE_FAIL);
		}
		// 构建返回结果数据
		Map<String, String> result = new HashMap<String, String>();
		result.put("prepayId", unifiedOrderReturn.getPrepayId());
		result.put("nonceStr", unifiedOrderReturn.getNonceStr());
		result.put("tradeType", unifiedOrderReturn.getTradeType());
		result.put("codeUrl", unifiedOrderReturn.getCodeUrl());
		result.put("sign", unifiedOrderReturn.getSign());
		result.put("deviceInfo", unifiedOrderReturn.getDeviceInfo());
		return Result.ok().put("result", result);
	}

	/**
	 * 构建参数
	 * 
	 * @param orderId
	 * @param productName
	 * @param price
	 * @param deviceInfo
	 * @return
	 */
	public UnifiedOrder buildParam(String orderId, String productName, String price, String deviceInfo) {
		UnifiedOrder unifiedOrder = new UnifiedOrder();
		unifiedOrder.setAppid(weixinAppId);
		unifiedOrder.setMchId(weixinMchId);
		unifiedOrder.setBody(productName);
		// 微信订单号不允许重复,避免不能重复请求支付二维码
		orderId = orderId + Const.SYMBOL_UNDERLINE + System.currentTimeMillis() / 1000;
		unifiedOrder.setOutTradeNo(orderId);
		unifiedOrder.setNonceStr(WeixinPayUtil.getUUID());
		BigDecimal bigDecimal = new BigDecimal(price);
		BigDecimal bigDecimalModify = new BigDecimal("100");
		unifiedOrder.setTotalFee(bigDecimalModify.multiply(bigDecimal).intValue());
		unifiedOrder.setNotifyUrl(weixinNotifyUrl);
		unifiedOrder.setTradeType(tradeType);
		unifiedOrder.setSpbillCreateIp(IPUtil.getLocalAddr());
		unifiedOrder.setDeviceInfo(deviceInfo);
		return unifiedOrder;
	}

	/**
	 * 更新支付渠
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean updatePayChennel(String orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ORDER_ID", orderId);
		params.put("PAY_CHANNEL", PayChannel.PAY_CHANNEL_WEIXIN.getValue());
		int result = masterOrderMapper.updatePayChennel(params);
		if (result < 1) {
			LOGGER.error("ORDER_ID:{},更新支付渠道失败！");
			return false;
		}
		return true;
	}

	/**
	 * 参数校验
	 * 
	 * @param param
	 * @return
	 */
	public Result precreateValidateParam(String orderId, String productName, String price, String productDesc) {
		if (StringUtils.isBlank(orderId)) {
			LOGGER.error("ORDER_ID 不能为空！");
			return Result.error(ErrorCode.PARAMETER_NULL_ERROR);
		}
		if (StringUtils.isBlank(productName)) {
			LOGGER.error("PRODUCT_NAME 不能为空！");
			return Result.error(ErrorCode.PARAMETER_NULL_ERROR);
		}
		if (StringUtils.isBlank(price)) {
			LOGGER.error("PRICE 不能为空！");
			return Result.error(ErrorCode.PARAMETER_NULL_ERROR);
		}
		if (StringUtils.isBlank(productDesc)) {
			LOGGER.error("PRODUCT_DESC 不能为空！");
			return Result.error(ErrorCode.PARAMETER_NULL_ERROR);
		}
		return null;
	}

}
