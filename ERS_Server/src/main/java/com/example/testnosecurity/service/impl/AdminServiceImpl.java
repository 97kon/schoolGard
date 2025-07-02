package com.example.testnosecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testnosecurity.pojo.Admin;
import com.example.testnosecurity.service.AdminService;
import com.example.testnosecurity.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author liuqiming
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2025-04-17 20:28:49
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{
    
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean addAdmin(Admin admin) {
        try {
            // 检查管理员是否已存在
            Admin existAdmin = lambdaQuery()
                    .eq(Admin::getAdusername, admin.getAdusername())
                    .one();
            if (existAdmin != null) {
                return false;
            }
            // 保存新管理员
            return save(admin);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(String adminId, String newPassword) {
        try {
            // 查询管理员
            Admin admin = lambdaQuery()
                    .eq(Admin::getAdusername, adminId)
                    .one();
            if (admin == null) {
                return false;
            }
            // 更新密码
            admin.setAdpwd(newPassword);
            return updateById(admin);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
