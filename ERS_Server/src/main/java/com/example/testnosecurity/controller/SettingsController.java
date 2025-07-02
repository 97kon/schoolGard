package com.example.testnosecurity.controller;

import com.example.testnosecurity.pojo.Settings;
import com.example.testnosecurity.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin("http://localhost:8093")
@RequestMapping("/api/manage")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/getsettings")
    public Map<String, Object> getSettings() {
        Map<String, Object> result = new HashMap<>();
        try {
            Settings settings = settingsService.getSettings();
            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", settings);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取设置失败");
        }
        return result;
    }

    @PostMapping("/updatesettings")
    public Map<String, Object> updateSettings(@RequestBody Settings settings) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = settingsService.updateSettings(settings);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新设置失败");
        }
        return result;
    }
}
