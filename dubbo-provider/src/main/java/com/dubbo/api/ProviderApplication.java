package com.dubbo.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.desaysv.vehicle.dao")
public class ProviderApplication {
    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProviderApplication.class, args);
        ProviderApplication.applicationContext = context;
    }

}
