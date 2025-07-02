package com.example.testnosecurity.controller;

import com.example.testnosecurity.pojo.Opentime;
import com.example.testnosecurity.service.OpentimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin("http://localhost:8093")
@RequestMapping("/api/manage")
public class OpentimeController {

    @Autowired
    private OpentimeService opentimeService;

    @GetMapping("/getDisabledTimes")
    public Map<String, Object> getDisabledTimes() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Opentime> disabledTimes = opentimeService.getAllOpentimes();
            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", disabledTimes);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取禁止时间段失败");
        }
        return result;
    }

    @PostMapping("/addOpentime")
    public Map<String, Object> addOpentime(@RequestBody Opentime opentime) {
        int threeDigitNumber = 100 + (int) (Math.random() * 900);
        opentime.setOid(threeDigitNumber);
        System.out.println(opentime.getOstartdatetime().getClass());
        System.out.println(opentime);
        return opentimeService.addOpentime(opentime);
    }
}
