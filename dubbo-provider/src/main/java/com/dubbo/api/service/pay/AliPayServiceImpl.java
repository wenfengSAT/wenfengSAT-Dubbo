package com.dubbo.api.service.pay;

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
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.dubbo.api.constant.ErrorCode;
import com.dubbo.api.constant.PayChannel;
import com.dubbo.api.constant.Result;
import com.dubbo.api.dao.MasterOrderMapper;

/**
 * 
 * @Description： 支付宝支付
 * 
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月29日上午11:47:33]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Service(version = "1.0.0")
public class AliPayServiceImpl implements AliPayService {

	@Value("${alipay_private_key}")
	private String privateKey;
	@Value("${alipay_public_key}")
	private String alipayPublicKey;
	@Value("${alipay_app_id}")
	private String appId;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(AliPayServiceImpl.class);

	@Autowired
	private MasterOrderMapper masterOrderMapper;

	/**
	 * 获取支付宝支付二维码接口
	 */
	@Override
	public Result alipayPrecreate(Map<String, String> param) {
		LOGGER.info("alipayPrecreate params:{}", JSON.toJSONString(param));
		String orderId = param.get("ORDER_ID");
		String productName = param.get("PRODUCT_NAME");
		String price = param.get("PRICE");
		String productDesc = param.get("PRODUCT_DESC");
		Result validateResult = precreateValidateParam(orderId, productName, price, productDesc);
		// 校验参数
		if (!Objects.isNull(validateResult)) {
			LOGGER.error("参数校验失败！{}", JSON.toJSONString(validateResult));
			return validateResult;
		}
		// 更新支付渠道为支付宝
		if (!updatePayChennel(orderId)) {
			LOGGER.error("更新支付渠道为支付宝失败！orderId:{}", orderId);
			return Result.error(ErrorCode.UPDATE_PAYCHENNEL_FAIL);
		}
		AlipayClient alipayClient = new DefaultAlipayClient(serviceUrl, appId, privateKey, format, charset,
				alipayPublicKey, signType);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setSubject(productName);
		model.setOutTradeNo(orderId);
		model.setTotalAmount(price);
		model.setBody(productDesc);

		request.setBizModel(model);
		request.setNotifyUrl(notifyUrl);

		// 支付宝执行流程
		AlipayTradePrecreateResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			LOGGER.error("调用支付宝获取支付二维码失败！=={}", e);
			return Result.error(ErrorCode.ALIPAY_REQUEST_QRCODE_FAIL);
		}
		if (null == response || !response.isSuccess()) {
			return Result.error(ErrorCode.ALIPAY_REQUEST_QRCODE_FAIL);
		}
		String payResult = response.getBody();
		LOGGER.info("orderId:{},payResult=={}", orderId, payResult);
		if (!response.getCode().equals("10000")) {
			LOGGER.error("请求支付宝支付二维码失败!");
			Result.error(response.getSubCode(), response.getSubMsg());
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("outTradeNo", response.getOutTradeNo());
		result.put("qrCode", response.getQrCode());
		JSONObject jsonObject = JSONObject.parseObject(payResult);
		result.put("sign", jsonObject.getString("sign"));
		return Result.ok().put("result", result);
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
		params.put("PAY_CHANNEL", PayChannel.PAY_CHANNEL_ALIPAY.getValue());
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
