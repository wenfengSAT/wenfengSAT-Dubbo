package com.cheng.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cheng.provider.service.ProviderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Reference(version = "1.0.0", application = "${dubbo.application.id}", url = "dubbo://localhost:12345")
    private ProviderService providerService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return providerService.sayHello(name);
    }
}
