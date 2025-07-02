package com.example.testnosecurity.controller;

import com.example.testnosecurity.aliyun.SendSms;
import com.example.testnosecurity.pojo.Admin;
import com.example.testnosecurity.pojo.User;
import com.example.testnosecurity.service.AdminService;
import com.example.testnosecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8093", "http://localhost:8090", "http://localhost:8095"})
public class UserController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;


    SendSms sendSms = new SendSms();

    @PostMapping("/api/manage/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        Map<String, Object> result = new HashMap<>();
        
        // 查询管理员
        Admin admin = adminService.lambdaQuery()
                .eq(Admin::getAdusername, username)
                .one();
                
        if (admin == null) {
            result.put("code", 400);
            result.put("message", "账号不存在");
            return result;
        }
        
        if (!admin.getAdpwd().equals(password)) {
            result.put("code", 400);
            result.put("message", "密码错误");
            return result;
        }
        
        result.put("code", 200);
        result.put("message", "登录成功");
        return result;
    }

    @PostMapping("/api/client/register")
    public Map<String, Object> register(@RequestBody User user) {
        System.out.println(user);
        Map<String, Object> result = new HashMap<>();
        try {
            // 设置默认值
            user.setBlacklist(0);
            user.setCreatetime(new Date());
            user.setEnblacklisttime(null);
            user.setUpic("/img/1111.png");
            
            boolean success = userService.save(user);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "注册成功" : "注册失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "注册失败");
        }
        return result;
    }

    @PostMapping("/api/Client/login")
    public Map<String, Object> clientLogin(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        System.out.println(username);
        System.out.println(password);
        
        Map<String, Object> result = new HashMap<>();
        
        // 查询用户
        User user = userService.lambdaQuery()
                .eq(User::getUname, username)
                .one();
                
        if (user == null) {
            result.put("code", 400);
            result.put("message", "账号不存在");
            return result;
        }
        
        if (!user.getUpwd().equals(password)) {
            result.put("code", 400);
            result.put("message", "密码错误");
            return result;
        }
        
        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("data", user);
        return result;
    }

    @GetMapping("/api/manage/user/list")
    public Map<String, Object> getUserList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String idNumber,
            @RequestParam(required = false) Boolean isBlacklisted,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        Map<String, Object> result = new HashMap<>();
        try {
            List<User> userList = userService.getUserList(name, phone, idNumber, isBlacklisted, startTime, endTime);
            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", userList);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取用户列表失败");
        }
        return result;
    }

    @PostMapping("/api/manage/user/update")
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.updateUser(user);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "更新成功" : "更新失败");
            System.out.println("状态为："+success);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新用户信息失败");
        }
        return result;
    }

    @PostMapping("/api/manage/user/delete")
    public Map<String, Object> deleteUser(@RequestParam Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.deleteUser(id);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "删除用户失败");
        }
        return result;
    }

    @PostMapping("/api/client/user/getveritycode")
    public Map<String, Object> GetVerityCode(@RequestParam String uphone) throws Exception {
        System.out.println("前端传进来的手机号"+uphone);

        int threeDigitNumber = 1000 + (int) (Math.random() * 9000);
        String code = String.valueOf(threeDigitNumber);
        System.out.println("当前生成的验证码为："+code);

        boolean isSendSms = sendSms.SendCode(uphone,code);
        System.out.println("验证码发送结果为："+isSendSms);

        Map<String, Object> result = new HashMap<>();
        try{
            boolean success = userService.updateUserCodeByUphone(uphone, code);
            System.out.println("验证码创建结果为："+success);
            if (isSendSms && success){
                result.put("code", 200);
                result.put("message", "发送和创建成功");
            }else {
                result.put("code",400);
                result.put("message","发送或创建失败");
            }


        }catch (Exception e){
            result.put("code", 500);
            result.put("message", "验证码创建异常");
        }
        return  result;

    }

    @PostMapping("/api/client/user/clientloginbyuphone")
    public Map<String, Object> ClientLoginByUphone(@RequestParam String uphone,
                                                   @RequestParam String code){
//        String uphone = params.get("uphone");
//        String code = params.get("code");
        System.out.println("uphone为："+uphone);
        System.out.println("code为:"+code);
        User user = userService.lambdaQuery()
                .eq(User::getUphone, uphone)
                .one();
        System.out.println("查询出来的user为"+user);
        Map<String, Object> result = new HashMap<>();
        try{
            String ucode = userService.getUserCodeByUphone(uphone);
            if (Objects.equals(ucode, code)){
                result.put("code", 200);
                result.put("message", "验证码正确");
                result.put("data",user);
            }else {
                result.put("code",400);
                result.put("message","验证码不正确");
            }

        }catch(Exception e){
            result.put("code",500);
            result.put("message","异常-失败");
        }
        return  result;
    }

    public Map<String, Object> getUserListByUphone(@RequestParam String uphone){
        System.out.println("获取到的手机号为"+uphone);
        // 查询用户
        User user = userService.lambdaQuery()
                .eq(User::getUphone, uphone)
                .one();
        Map<String, Object> result = new HashMap<>();

        return  result;
    }
}
