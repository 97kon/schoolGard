package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
* @author liuqiming
* @description 针对表【user】的数据库操作Service
* @createDate 2025-04-17 20:28:49
*/
public interface UserService extends IService<User> {
    List<User> getUserList(String name, String phone, String idNumber, 
                         Boolean isBlacklisted, String startTime, String endTime);
    
    boolean updateUser(User user);
    void updateUserBlacklistStatus(User user);
    List<User> getUsersToRelease(int limitDays);
    
    boolean deleteUser(Integer id);

    boolean updateUserCodeByUphone(String uphone,String code);

    String getUserCodeByUphone(String uphone);
}
