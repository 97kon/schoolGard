package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.Noshow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuqiming
* @description 针对表【noshow】的数据库操作Service
* @createDate 2025-04-17 20:28:49
*/
import java.util.List;

public interface NoshowService extends IService<Noshow> {
    List<String> getUsersReachingThreshold(int threshold);
    void cleanOldNoshowRecords(int days);
}
