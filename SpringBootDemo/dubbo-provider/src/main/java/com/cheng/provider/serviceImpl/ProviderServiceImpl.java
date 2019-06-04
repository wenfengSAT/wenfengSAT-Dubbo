package com.cheng.provider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cheng.provider.service.ProviderService;

@Service(version = "1.0.0", application = "${dubbo.application.id}", protocol = "${dubbo.protocol.id}", registry = "${dubbo.registry.id}")
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String sayHello(String name) {

        return "天上掉下个猪八戒 ======" + name;
    }
}
