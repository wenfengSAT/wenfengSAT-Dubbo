package com.dubbo.api.service.util.wxpay;

/**
 * 
 * @Description： 微信统一下单 接口返回参数模型
 * 
 * @author [ Wenfeng.Huang ] on [2019年5月31日上午10:39:08]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class UnifiedOrderReturn {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";

	private String returnCode;// SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private String returnMsg;// 返回信息，如非空，为错误原因 如，签名失败 ,参数格式校验错误
	private String appid; // 公众账号ID
    private String mchId; // 微信支付商户号
    private String deviceInfo; // 设备号
    private String nonceStr; // 随机字符串
    private String sign; // 签名
    private String resultCode; // 业务结果 SUCCESS/FAIL
    private String errCode; // 错误代码
    private String errCodeDes; // 错误代码描述
    // 以下字段 在return_code 和result_code都为SUCCESS的时候有返回
    private String tradeType; // 交易类型 TradeType
    private String prepayId; // 预支付交易会话标识 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
    private String codeUrl; // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
    
    
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

}
