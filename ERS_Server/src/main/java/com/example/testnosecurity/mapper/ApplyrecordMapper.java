package com.example.testnosecurity.mapper;

import com.example.testnosecurity.pojo.Applyrecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
* @author liuqiming
* @description 针对表【applyrecord】的数据库操作Mapper
* @createDate 2025-04-17 20:28:49
* @Entity com.example.testnosecurity.pojo.Applyrecord
*/
@Mapper
public interface ApplyrecordMapper extends BaseMapper<Applyrecord> {

    @Delete("DELETE FROM applyrecord WHERE anumbering = #{anumbering}")
    boolean deleteByAnumberingBoolean(String anumbering);
    
    @Select("SELECT * FROM applyrecord WHERE astatus = 1 AND DATE_ADD(aentertime, INTERVAL #{lateMinutes} MINUTE) < NOW()")
    List<Applyrecord> selectOverdueRecords(@Param("lateMinutes") int lateMinutes);
}
