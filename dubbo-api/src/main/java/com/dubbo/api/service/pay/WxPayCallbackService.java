package com.dubbo.api.service.pay;

import java.util.Map;

import com.dubbo.api.constant.Result;


/**
 * 
 * @Description： 微信回调接口
 * @author [ Wenfeng.Huang ] on [2019年5月31日下午8:58:42]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface WxPayCallbackService {

	Result wxpayNotify(Map<String,Object> param);
}
