package com.dubbo.api.constant;

/**
 * 
 * @Description： 订单支付状态
 * 
 * @author [ Wenfeng.Huang ] on [2019年5月29日上午11:55:50]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum OrderStatus {

	UNPAID("0", "未支付"),
	PAID("1", "已支付"),
	CANCELLED("2", "已取消");

	private String value;
	private String desc;

	private OrderStatus(String value, String desc) {
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
