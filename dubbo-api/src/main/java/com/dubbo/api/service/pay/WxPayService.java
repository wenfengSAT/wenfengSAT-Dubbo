package com.dubbo.api.service.pay;

import java.util.Map;

import com.dubbo.api.constant.Result;


/**
 * 
 * @Description： 微信支付
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月31日上午9:04:51]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface WxPayService {
	
	/**
	 * 获取支付宝支付二维码
	 * @param param
	 * @return
	 */
	Result wxpayPrecreate(Map<String,String> param);

}
