# 定时任务实现文档

## 功能概述
本定时任务实现了以下功能：
1. 每天凌晨0点自动扫描noshow表
2. 将爽约次数达到阈值的用户加入黑名单
3. 自动清理30天前的noshow记录
4. 自动释放达到限制天数的黑名单用户

## 核心实现类

### 1. NoshowCheckTask (定时任务主类)
- 路径: `src/main/java/com/example/testnosecurity/task/NoshowCheckTask.java`
- 功能:
  - 使用`@Scheduled(cron = "0 0 0 * * ?")`注解实现每天0点执行
  - 调用各Service完成业务逻辑
  - 使用`@Transactional`保证事务一致性

### 2. NoshowServiceImpl
- 路径: `src/main/java/com/example/testnosecurity/service/impl/NoshowServiceImpl.java`
- 核心方法:
  - `getUsersReachingThreshold()`: 查询达到爽约次数的用户
  - `cleanOldNoshowRecords()`: 清理30天前的记录

### 3. UserServiceImpl
- 路径: `src/main/java/com/example/testnosecurity/service/impl/UserServiceImpl.java`  
- 核心方法:
  - `updateUserBlacklistStatus()`: 更新用户黑名单状态和时间
  - `getUsersToRelease()`: 查询需要释放的黑名单用户

### 4. SettingsServiceImpl
- 路径: `src/main/java/com/example/testnosecurity/service/impl/SettingsServiceImpl.java`
- 核心方法:
  - `getNoshowTimes()`: 获取爽约次数阈值
  - `getLimitDays()`: 获取黑名单限制天数

## 数据库交互
- 使用MyBatis Plus进行数据库操作
- 主要涉及表:
  - `noshow`: 存储用户爽约记录
  - `user`: 存储用户信息(含黑名单状态)
  - `settings`: 存储系统配置参数

## 事务处理
- 在`NoshowCheckTask`类使用`@Transactional`注解
- 保证以下操作的原子性:
  1. 释放到期黑名单用户
  2. 查询爽约用户
  3. 更新黑名单状态
  4. 清理旧记录

## 配置说明
- 爽约阈值: 通过`settings`表的`noshowtimes`字段配置
- 黑名单限制天数: 通过`settings`表的`limitdays`字段配置
- 数据保留天数: 固定30天(可在代码中修改)
- 执行时间: 每天0点(可通过cron表达式调整)

## 扩展建议
1. 可添加日志记录每次任务执行情况
2. 可增加邮件/短信通知功能
3. 可考虑将30天配置也放入settings表
