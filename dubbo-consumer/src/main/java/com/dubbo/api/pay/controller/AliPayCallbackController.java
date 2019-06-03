package com.dubbo.api.pay.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.dubbo.api.constant.ErrorCode;
import com.dubbo.api.constant.Result;
import com.dubbo.api.service.pay.AliPayCallbackService;

/**
 * 
 * @Description： 支付宝回调接口
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年6月3日下午2:15:00]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@RestController
public class AliPayCallbackController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AliPayCallbackController.class);
	
	@Reference(version = "1.0.0")
	private AliPayCallbackService aliPayCallbackService;

	/**
	 * 支付宝回调接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/alipay/notify", method = RequestMethod.POST)
	public Map<String, String> alipayNotify(HttpServletRequest request) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
			params.put(name, valueStr);
		}
		LOGGER.info("alipayNotify params:{}", JSON.toJSONString(params));
		Result notifyResult = aliPayCallbackService.alipayNotify(params);
		String resultCode = (String) notifyResult.get("resultCode");
		Map<String,String> result = new HashMap<String,String>();
		if(ErrorCode.SUCCESS.getCode().equals(resultCode)) {
			result.put("returnState", "success");
		}else {
			LOGGER.info("alipayNotify fail params:{}", JSON.toJSONString(params));
			result.put("returnState", "fail");
		}
		return result;
	}
}
