package com.dubbo.api.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @Description： 返回结果
 * @author [ Wenfeng.Huang ] on [2018年10月10日下午7:37:38]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("resultCode", "0000");
		put("resultDesc", "操作成功！");
	}
	
	
	public static Result error(String code, String msg) {
		Result r = new Result();
		r.put("resultCode", code);
		r.put("resultDesc", msg);
		return r;
	}
	
	public static Result error(ErrorCode errorCode) {
		Result r = new Result();
		r.put("resultCode", errorCode.getCode());
		r.put("resultDesc", errorCode.getMsg());
		return r;
	}
	
	public static Result ok() {
		return new Result();
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("resultDesc", msg);
		return r;
	}
	
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
