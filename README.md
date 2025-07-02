---
typora-root-url: Images
---

# schoolGard
高校入校登记系统

本系统主要用于华北科技学院的入校预约登记系统

有小程序端和后台管理端

主要的技术栈有springboot3+vue3+uniapp+mybatisplus+mysql

界面展示如下

小程序端：

登录页（用户名+密码登录）

<img src="/小程序登录1.png" alt="/" style="zoom:60%;" />

小程序登录页（手机号验证码验证登录）

<img src="/小程序登录2.png" style="zoom:60%;" />

小程序注册页：

<img src="/小程序注册.png" style="zoom:60%;" />

登记申请页：

<img src="/申请登记页.png" style="zoom:60%;" />

查看个人申请页：

<img src="/查看个人申请页.png" style="zoom:60%;" />

个人中心页：

<img src="/个人中心页.png" style="zoom:60%;" />

修改用户信息页：

<img src="/修改用户信息页.png" style="zoom:60%;" />

签到页：

<img src="/签到页.png" style="zoom:60%;" />

## 后台管理端：

后台登录页面：

![](/HT/HTlogin.png)

后台主页：

![](/HT/HTIndex.png)

账号列表：

![](/HT/账号列表.png)

添加管理员账号：

![](/HT/添加管理员账号.png)

修改管理员密码：

![](/HT/修改管理员密码.png)

查询入校记录：

![](/HT/查询入校记录.png)

设置页1（设置迟到时间，爽约条件，和黑名单限制条件）：

![](/HT/设置页1.png)

设置页2（设置学校不开放的时间段）：

![](/HT/设置页2.png)

审批入校登记：

![](/HT/审批入校登记.png)

如果要使用手机号验证登录功能，先在server里面重新填入阿里云短信SDK的的密钥，设置如下：

![](/短信SDK设置1.png)

![](/短信SDK设置2.png)