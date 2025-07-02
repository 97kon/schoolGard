package com.example.testnosecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testnosecurity.pojo.User;
import com.example.testnosecurity.service.UserService;
import com.example.testnosecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author liuqiming
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-04-17 20:28:49
*/
import java.util.List;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserList(String name, String phone, String idNumber, 
                                Boolean isBlacklisted, String startTime, String endTime) {
        return lambdaQuery()
            .like(name != null, User::getUname, name)
            .like(phone != null, User::getUphone, phone)
            .like(idNumber != null, User::getUidnumber, idNumber)
            .eq(isBlacklisted != null, User::getBlacklist, isBlacklisted)
            .ge(startTime != null, User::getCreatetime, startTime)
            .le(endTime != null, User::getCreatetime, endTime)
            .list();
    }

    @Override
    public boolean updateUser(User user) {
        return updateById(user);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return removeById(id);
    }

    @Override
    public void updateUserBlacklistStatus(User user) {
        user.setBlacklist(1);
        user.setEnblacklisttime(new Date());
        updateById(user);
    }

    @Override
    public List<User> getUsersToRelease(int limitDays) {
        Date cutoffDate = new Date(System.currentTimeMillis() - (long) limitDays * 24 * 60 * 60 * 1000);
        return lambdaQuery()
            .eq(User::getBlacklist, 1)
            .le(User::getEnblacklisttime, cutoffDate)
            .list();
    }
    @Override
    public boolean updateUserCodeByUphone(String uphone,String code){
        return lambdaUpdate()
                .eq(User::getUphone, uphone)
                .set(User::getCode, code)
                .update();
    }
    @Override
    public String getUserCodeByUphone(String uphone){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        System.out.println("查询");

         lambdaQueryWrapper
                .eq(User::getUphone, uphone)
                 .select(User::getCode)
                .last("LIMIT 1");
         List<Object> codes = userMapper.selectObjs(lambdaQueryWrapper);
         if (!codes.isEmpty()){
             String code = (String) codes.get(0);
             return code;
         }
         return null;

    }
}
