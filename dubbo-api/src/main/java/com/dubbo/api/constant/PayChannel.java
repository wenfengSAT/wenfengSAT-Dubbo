package com.dubbo.api.constant;

/**
 * 
 * @Description： 支付渠道
 * 
 * @author [ Wenfeng.Huang ] on [2019年5月29日上午11:55:50]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum PayChannel {

	PAY_CHANNEL_ALIPAY("1", "支付宝支付"),
	PAY_CHANNEL_WEIXIN("2", "微信支付");

	private String value;
	private String desc;

	private PayChannel(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	
}
