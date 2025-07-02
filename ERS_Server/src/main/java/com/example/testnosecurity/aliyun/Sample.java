package com.example.testnosecurity.aliyun;

import com.aliyun.ecs20140526.Client;
import com.aliyun.teaopenapi.models.Config;

public class Sample {
    public static void main(String[] args)  throws  Exception{
//        初始化客户端
        Config config =new Config()
                .setAccessKeyId(System.getenv(""))
                .setAccessKeySecret(System.getenv(""));
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
//        构建请求对象

    }
}
