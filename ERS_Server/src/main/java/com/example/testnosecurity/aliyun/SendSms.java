package com.example.testnosecurity.aliyun;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class SendSms {
    public boolean SendCode(String uphone, String code) throws Exception{
//    public static void main(String[] args) throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        String templateParam = new Gson().toJson(params);
        try {
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId("")
                    .accessKeySecret("")
                    .build());
            AsyncClient client = AsyncClient.builder()
                    .region("cn-chengdu")
                    .credentialsProvider(provider)
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    .setEndpointOverride("dysmsapi.aliyuncs.com")
                    )
                    .build();
            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .signName("阿里云短信测试")
                    .templateCode("SMS_154950909")
                    .phoneNumbers(uphone)
//                .templateParam("{\"code\":\"6666\"}")
                    .templateParam(templateParam)
                    .build();

//        异步获取响应结果
            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
            SendSmsResponse resp = response.get();
            boolean isSendSms;
            if (resp.getStatusCode() == 200) {
                isSendSms = true;
            } else {
                System.out.println("状态码为：" + resp.getStatusCode());
                isSendSms = false;
            }
            System.out.println(new Gson().toJson(resp));

            client.close();
            return isSendSms;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
