package com.dubbo.api.service.sys;

import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.service.sys.AcctService;

@Service(version = "1.0.0")
public class AcctServiceImpl implements AcctService{

	@Override
	public Map<String, Object> queryAcct(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
