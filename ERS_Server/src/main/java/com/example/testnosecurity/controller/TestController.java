package com.example.testnosecurity.controller;

import com.example.testnosecurity.aliyun.SendSms;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping
public class TestController {
    @GetMapping("/test")
    public boolean TestDemo() throws Exception {
        String phone = "13980194399";
        int threeDigitNumber = 1000 + (int) (Math.random() * 9000);
        String code = String.valueOf(threeDigitNumber);
        System.out.println(phone);
        System.out.println(code);
        boolean isSendOK = new SendSms().SendCode(phone,code);
        return isSendOK;
    }
}
