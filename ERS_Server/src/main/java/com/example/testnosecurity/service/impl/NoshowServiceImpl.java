package com.example.testnosecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testnosecurity.pojo.Noshow;
import com.example.testnosecurity.service.NoshowService;
import com.example.testnosecurity.mapper.NoshowMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoshowServiceImpl extends ServiceImpl<NoshowMapper, Noshow>
    implements NoshowService {

    @Override
    public List<String> getUsersReachingThreshold(int threshold) {
        return baseMapper.selectUsersReachingThreshold(threshold);
    }

    @Override
    public void cleanOldNoshowRecords(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        lambdaUpdate()
            .lt(Noshow::getNdatetime, cutoffDate)
            .remove();
    }
}
