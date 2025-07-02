package com.example.testnosecurity.task;

import com.example.testnosecurity.service.NoshowService;
import com.example.testnosecurity.service.SettingsService;
import com.example.testnosecurity.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.example.testnosecurity.pojo.User;

@Component
public class NoshowCheckTask {

    private final NoshowService noshowService;
    private final SettingsService settingsService;
    private final UserService userService;

    public NoshowCheckTask(NoshowService noshowService, 
                         SettingsService settingsService,
                         UserService userService) {
        this.noshowService = noshowService;
        this.settingsService = settingsService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨0点执行
    @Transactional
    public void checkAndUpdateBlacklist() {
        // 1. 处理黑名单用户
        releaseBlacklistedUsers();
        
        // 2. 获取爽约次数阈值
        int threshold = settingsService.getNoshowTimes();
        
        // 3. 获取达到阈值的用户名单
        List<String> users = noshowService.getUsersReachingThreshold(threshold);
        
        // 4. 将用户加入黑名单
        users.forEach(username -> {
            User user = new User();
            user.setUname(username);
            userService.updateUserBlacklistStatus(user);
        });
        
        // 5. 清理30天前的noshow记录
        noshowService.cleanOldNoshowRecords(30);
    }

    private void releaseBlacklistedUsers() {
        // 获取黑名单限制天数
        int limitDays = settingsService.getLimitDays();
        
        // 查询需要释放的用户
        List<User> usersToRelease = userService.getUsersToRelease(limitDays);
        
        // 释放用户
        usersToRelease.forEach(user -> {
            user.setBlacklist(0);
            user.setEnblacklisttime(null);
            userService.updateUser(user);
        });
    }
}
