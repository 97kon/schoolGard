package com.example.testnosecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class TestDemo {
    @Test
    public static void main(String[] args) {
        String code = "6666";
//                            .templateParam("{\"code\":\"“+code+”\"}")
        System.out.println("{\"code\":\"“+code+”\"}");

    }
}
