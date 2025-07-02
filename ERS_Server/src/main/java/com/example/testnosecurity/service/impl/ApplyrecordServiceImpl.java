package com.example.testnosecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testnosecurity.pojo.Applyrecord;
import com.example.testnosecurity.pojo.Noshow;
import com.example.testnosecurity.service.ApplyrecordService;
import com.example.testnosecurity.service.NoshowService;
import com.example.testnosecurity.service.SettingsService;
import com.example.testnosecurity.mapper.ApplyrecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.List;

/**
* @author liuqiming
* @description 针对表【applyrecord】的数据库操作Service实现
* @createDate 2025-04-17 20:28:49
*/
@Service
@EnableScheduling
public class ApplyrecordServiceImpl extends ServiceImpl<ApplyrecordMapper, Applyrecord>
    implements ApplyrecordService {

    @Autowired
    private ApplyrecordMapper applyrecordMapper;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private NoshowService noshowService;

    @Scheduled(cron = "0 */1 * * * ?") // 每5分钟执行一次
    public void checkMissedAppointments() {
        // 1. 获取settings中的late值
        int lateMinutes = settingsService.getLateMinutes();

        // 2. 查询已通过(astatus=1)且超时的申请记录
        List<Applyrecord> overdueRecords = applyrecordMapper.selectOverdueRecords(lateMinutes);

        // 3. 处理每条超时记录
        for (Applyrecord record : overdueRecords) {
            // 更新状态为爽约(4)
            record.setAstatus(4);
            updateById(record);

            // 创建noshow记录
            Noshow noshow = new Noshow();
            noshow.setNname(record.getAname());
            noshow.setNanumbering(record.getAnumbering());
            noshow.setNdatetime(new Date());
            noshowService.save(noshow);
        }
    }
    
    @Override
    public List<Applyrecord> getApplyrecordList(){
        return this.list();
    }

    @Override
    public boolean updateApplyrecord(Applyrecord applyrecord){
        return updateById(applyrecord);
    }

    @Override
    public boolean deleteApplyrecord(String anumbering){
        return applyrecordMapper.deleteByAnumberingBoolean(anumbering);
    }

    @Override
    public List<Applyrecord> getPendingApplyrecords() {
        return lambdaQuery()
                .eq(Applyrecord::getAstatus, 0)
                .list();
    }

    @Override
    public boolean updateApplyrecordStatus(String anumbering, int status) {
        Applyrecord record = new Applyrecord();
        record.setAnumbering(anumbering);
        record.setAstatus(status);
        return updateById(record);
    }

    @Override
    public boolean insertApplyrecord(Applyrecord applyrecord) {
        return save(applyrecord);
    }

    @Override
    public List<Applyrecord> selectAllByAname(String aname) {
        QueryWrapper<Applyrecord> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("aname", aname);
        System.out.println("slectAllByAname查询的数据"+queryWrapper1);
        return applyrecordMapper.selectList(queryWrapper1);
    }

    @Override
    public Applyrecord selectLatestApprovedApply(String aname, Integer astatus) {
        QueryWrapper<Applyrecord> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("aname", aname)
                   .eq("astatus", astatus)
                   .orderByAsc("aentertime") // 按进入时间升序排序
                   .last("LIMIT 1"); // 只取最近一条记录
        System.out.println("selectLatestApprovedApply查询到的数据："+queryWrapper2);
//        QueryWrapper<Applyrecord> queryWrapper3 = new QueryWrapper<>();
//        queryWrapper3.eq("anama", aname)
//                .eq("astatus", astatus)
//                .orderByAsc("aentertime");
//        System.out.println("按照时间升序排序"+queryWrapper3);
        return applyrecordMapper.selectOne(queryWrapper2);
    }
}
