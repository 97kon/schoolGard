package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuqiming
* @description 针对表【admin】的数据库操作Service
* @createDate 2025-04-17 20:28:49
*/
public interface AdminService extends IService<Admin> {
    boolean addAdmin(Admin admin);
    boolean resetPassword(String adminId, String newPassword);
}
