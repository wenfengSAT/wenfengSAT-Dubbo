package com.dubbo.api.constant;

/**
 * 
 * @Description： 订单来源
 * 
 * @author [ Wenfeng.Huang ] on [2019年5月29日上午11:55:50]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum OrderSource {

	MEITUAN("1", "美团"),
	TUHU("2", "途虎"),
	ZIYING("3", "自营");

	private String value;
	private String desc;

	private OrderSource(String value, String desc) {
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
