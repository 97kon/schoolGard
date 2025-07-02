package com.example.testnosecurity.service.impl;

import com.example.testnosecurity.mapper.OpentimeMapper;
import com.example.testnosecurity.pojo.Opentime;
import com.example.testnosecurity.service.OpentimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class OpentimeServiceImpl implements OpentimeService {

    @Autowired
    private OpentimeMapper opentimeMapper;

    @Override
    public List<Opentime> getAllOpentimes() {
        return opentimeMapper.selectAll();
    }

    @Override
    public Map<String, Object> addOpentime(Opentime opentime) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("在实现类"+opentime);
        int affectedRows = opentimeMapper.insert(opentime);
        try {
//            int affectedRows = opentimeMapper.insert(opentime);
            result.put("code", affectedRows > 0 ? 200 : 400);
            result.put("message", affectedRows > 0 ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "添加禁止时间段失败");
        }
        return result;
    }
}
