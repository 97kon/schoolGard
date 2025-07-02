package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.Applyrecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liuqiming
* @description 针对表【applyrecord】的数据库操作Service
* @createDate 2025-04-17 20:28:49
*/
public interface ApplyrecordService extends IService<Applyrecord> {
    List<Applyrecord> getApplyrecordList();

    boolean updateApplyrecord(Applyrecord applyrecord);

    boolean deleteApplyrecord(String anumbering);

    List<Applyrecord> getPendingApplyrecords();

    boolean updateApplyrecordStatus(String anumbering, int status);
    
    boolean insertApplyrecord(Applyrecord applyrecord);
    
    List<Applyrecord> selectAllByAname(String aname);
    
    Applyrecord selectLatestApprovedApply(String aname, Integer astatus);
}
