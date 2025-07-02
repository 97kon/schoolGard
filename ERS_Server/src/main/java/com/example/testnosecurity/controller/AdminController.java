package com.example.testnosecurity.controller;

import com.example.testnosecurity.pojo.Admin;
import com.example.testnosecurity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8093")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/api/manage/admin/add")
    public Map<String, Object> addAdmin(@RequestBody Admin admin) {
//        System.out.println(admin);
        String isoTime = OffsetDateTime.now(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        // 将 ISO 字符串转为 Date
        OffsetDateTime odt = OffsetDateTime.parse(isoTime); // 解析为 OffsetDateTime
        Instant instant = odt.toInstant(); // 转为 Instant
        Date adcreatetime = Date.from(instant); // 转为 java.util.Date
        admin.setAdcreatetime(adcreatetime);
        int threeDigitNumber = 100 + (int) (Math.random() * 900);
        admin.setId(threeDigitNumber);
        System.out.println(admin);

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = adminService.addAdmin(admin);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "添加管理员成功" : "添加管理员失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "添加管理员异常");
        }
        return result;
    }

    @PostMapping("/api/manage/admin/resetPwd")
    public Map<String, Object> resetPassword(@RequestParam("adminId") String adminId,
                                           @RequestParam("newPassword") String newPassword) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(adminId);
        System.out.println(newPassword);
        try {
            boolean success = adminService.resetPassword(adminId, newPassword);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "密码重置成功" : "密码重置失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "密码重置异常");
        }
        return result;
    }
}
