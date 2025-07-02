package com.example.testnosecurity.controller;

import com.example.testnosecurity.pojo.Applyrecord;
import com.example.testnosecurity.service.ApplyrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
@CrossOrigin("http://localhost:8093")
public class ApplyrecordController {
    @Autowired
    private ApplyrecordService applyrecordService;


    @GetMapping("/api/manage/applyrecord/list")
    public Map<String,Object> getApplyrecord(){
        Map<String,Object> result = new HashMap<>();
        try{
            List<Applyrecord> applyrecordList = applyrecordService.getApplyrecordList();
            result.put("code",200);
            result.put("message", "获取成功");
            result.put("data", applyrecordList);
        } catch (Exception e){
            result.put("code", 500);
            result.put("message", "获取用户列表失败");
        }
        return result;
    }

    @GetMapping("/api/manage/applyrecord/pending")
    public Map<String,Object> getPendingApplyrecords(){
        Map<String,Object> result = new HashMap<>();
        try{
            List<Applyrecord> pendingList = applyrecordService.getPendingApplyrecords();
            result.put("code",200);
            result.put("message", "获取待审批记录成功");
            result.put("data", pendingList);
        } catch (Exception e){
            result.put("code", 500);
            result.put("message", "获取待审批记录失败");
        }
        return result;
    }

    @PostMapping("/api/manage/applyrecord/update")
    public Map<String, Object> updateApplyrecord(@RequestBody Applyrecord applyrecord){
        Map<String, Object> result = new HashMap<>();
        try{
            boolean success = applyrecordService.updateApplyrecord(applyrecord);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "更新成功" : "更新失败");
        }catch (Exception e){
            result.put("code", 500);
            result.put("message", "更新登记信息失败");
        }
        return result;
    }

    @PostMapping("/api/manage/applyrecord/approve")
    public Map<String, Object> approveApplyrecord(@RequestParam("anumbering") String anumbering){
        System.out.println(anumbering);
        Map<String, Object> result = new HashMap<>();
        try{
            boolean success = applyrecordService.updateApplyrecordStatus(anumbering, 1);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "审批通过成功" : "审批通过失败");
        }catch (Exception e){
            result.put("code", 500);
            result.put("message", "审批通过失败");
        }
        return result;
    }

    @PostMapping("/api/manage/applyrecord/reject")
    public Map<String, Object> rejectApplyrecord(@RequestParam("anumbering") String anumbering){
        Map<String, Object> result = new HashMap<>();
        try{
            boolean success = applyrecordService.updateApplyrecordStatus(anumbering, 2);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "拒绝成功" : "拒绝失败");
        }catch (Exception e){
            result.put("code", 500);
            result.put("message", "拒绝失败");
        }
        return result;
    }

    @PostMapping("/api/manage/applyrecord/delete")
    public  Map<String, Object> deleteApplyrecord(@RequestParam("anumbering") String anumbering){
        System.out.println(anumbering);
        System.out.println(anumbering.getClass());
        Map<String, Object> result = new HashMap<>();
        try{
            boolean success = applyrecordService.deleteApplyrecord(anumbering);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "删除成功" : "删除失败");
        }catch (Exception e){
            result.put("code", 500);
            result.put("message", "删除用户失败");
        }
        return  result;
    }

    @PostMapping("/api/client/insertrecord")
    public Map<String, Object> insertApplyrecord(@RequestBody Applyrecord applyrecord) {
        System.out.println("测试用数据"+applyrecord);
        Map<String, Object> result = new HashMap<>();
        try {
            // 设置默认状态为0(待审批)
            applyrecord.setAstatus(0);
            // 设置申请提交时间为当前时间
            applyrecord.setApplisubtime(new Date());

            boolean success = applyrecordService.insertApplyrecord(applyrecord);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "提交成功" : "提交失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "提交申请失败");
        }
        return result;
    }

    @GetMapping("/api/client/selectallByaname")
    public Map<String, Object> selectAllByAname(@RequestParam("aname") String aname) {
        System.out.println(aname);
        System.out.println(aname.getClass());
        Map<String, Object> result = new HashMap<>();
        try {
            List<Applyrecord> records = applyrecordService.selectAllByAname(aname);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", records);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败");
        }
        return result;
    }

    @GetMapping("/api/client/updateastatusByanumbering")
    public Map<String, Object> updateStatusByAnumbering(@RequestParam("anumbering") String anumbering) {

        System.out.println("更新状态请求参数anumbering: " + anumbering);
        Map<String, Object> result = new HashMap<>();

        try {
            boolean success = applyrecordService.updateApplyrecordStatus(anumbering, 3);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "取消申请成功" : "取消申请失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "取消申请失败");
        }
        return result;
    }

    @GetMapping("/api/client/updateastatusByanumberingandastatus")
    public Map<String, Object> updateStatusByAnumberingandastatus(
            @RequestParam("anumbering") String anumbering,
            @RequestParam("astatus") String astatus) {

        System.out.println("更新状态请求参数anumbering: " + anumbering);
        int astatusint = Integer.valueOf(astatus);
        System.out.println("astatusint"+astatusint);
        Map<String, Object> result = new HashMap<>();

        try {
            boolean success = applyrecordService.updateApplyrecordStatus(anumbering, astatusint);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "取消申请成功" : "取消申请失败");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "取消申请失败");
        }
        return result;
    }

    @GetMapping("/api/client/selectapplyrecordByanameandastatus")
    public Map<String, Object> selectLatestApprovedApply(
            @RequestParam("aname") String aname,
            @RequestParam("astatus") Integer astatus) {
        System.out.println(aname);
        System.out.println(astatus);
        Map<String, Object> result = new HashMap<>();

        try {
            Applyrecord record = applyrecordService.selectLatestApprovedApply(aname, astatus);
            System.out.println("输出selectLatestApprovedApply的record"+record);
            if (record != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", record);
            } else {
                result.put("code", 404);
                result.put("message", "未找到符合条件的申请记录");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败");
        }
        return result;
    }
}
