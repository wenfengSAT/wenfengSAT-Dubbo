package com.cheng.bootweb.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author ckj
 **/

@RestController
public class TestController {

    @GetMapping("/helloWorld")
    public void helloWorld() {
        System.out.println("login.....");
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login success...";
    }


}
