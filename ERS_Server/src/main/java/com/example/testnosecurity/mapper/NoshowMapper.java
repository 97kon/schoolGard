package com.example.testnosecurity.mapper;

import com.example.testnosecurity.pojo.Noshow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* @author liuqiming
* @description 针对表【noshow】的数据库操作Mapper
* @createDate 2025-04-17 20:28:49
* @Entity com.example.testnosecurity.pojo.Noshow
*/
@Mapper
public interface NoshowMapper extends BaseMapper<Noshow> {
    List<String> selectUsersReachingThreshold(@Param("threshold") int threshold);
}
