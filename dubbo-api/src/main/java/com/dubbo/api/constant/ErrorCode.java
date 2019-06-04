package com.dubbo.api.constant;

/**
 * 
 * @Description： 错误码定义枚举
 * 
 * @author [ Wenfeng.Huang ] on [2019年5月29日上午11:55:50]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum ErrorCode {
	SUCCESS("0000", "操作成功"),

	PARAMETER_ERROR("10100", "参数错误"),
	PARAMETER_NULL_ERROR("10101", "参数不能为空"),
	UPDATE_PAYCHENNEL_FAIL("10300", "更新支付渠道失败"),
	ORDER_NOT_EXIST("10301", "订单不存在"),
	AMOUNT_CHECK_ERROR("10302", "金额不相等"),
	UPDATE_PAY_STATUS_FAIL("10303", "更新订单状态失败"),
	
	ALIPAY_REQUEST_QRCODE_FAIL("10200", "请求支付宝支付二维码失败"),
	ALIPAY_ISP_UNKNOW_ERROR("20000", "服务不可用"),
	ALIPAY_AOP_INVALID_AUTH_TOKEN("20001", "授权权限不足"),
	ALIPAY_ISV_MISSING_METHOD("40001", "缺少方法名参数"),
	ALIPAY_SV_INVALID_PARAMETER("40002", "非法的参数"),
	ALIPAY_BUSINESS_PROCESSING_FAILED("40004", "业务处理失败"),
	ALIPAY_ISV_INSUFFICIENT_ISV_PERMISSIONS("40006", "权限不足"),
	ALIPAY_ORDER_IS_EXECUTED("10210", "订单号已执行"),
	ALIPAY_SIGN_VERIFIED_FAIL("10211", "支付宝验签异常"),
	ALIPAY_PARAMETER_APPID_CHECK_ERROR("10212", "appId校验失败"),
	ALIPAY_PARAMETER_SELLERID_CHECK_ERROR("10213", "sellerId校验失败"),
	
	WEIXIN_SIGN_CHECK_FAIL("10400", "微信支付返回结果签名校验不通过"),
	WEIXIN_REQUEST_QRCODE_FAIL("10401", "请求微信支付二维码失败"),
	WEIXIN_SIGN_VERIFIED_FAIL("10402", "微信验签异常"),
	WEIXIN_RETURN_CODE_FAIL("10403", "微信回调返回码不为SUCCESS"),
	WEIXIN_RETURN_PARAM_ERROR("10404", "回调参数错误"),
	WEIXIN_ORDER_IS_EXECUTED("10405", "订单号已执行"),
	
	
	;

	private String msg;
	private String code;

	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getCode() {
		return this.code;
	}
}
