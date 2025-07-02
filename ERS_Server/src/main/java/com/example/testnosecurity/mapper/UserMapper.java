package com.example.testnosecurity.mapper;

import com.example.testnosecurity.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liuqiming
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-04-17 20:28:49
* @Entity com.example.testnosecurity.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




