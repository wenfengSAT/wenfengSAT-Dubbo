package com.dubbo.api.service.pay;

import java.util.Map;

import com.dubbo.api.constant.Result;


/**
 * 
 * @Description： 支付宝支付
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月29日上午11:47:33]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface AliPayService {

	/**
	 * 支付宝支付二维码生成接口
	 * @param param
	 * @return
	 */
	Result alipayPrecreate(Map<String,String> param);
}
