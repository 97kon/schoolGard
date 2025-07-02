package com.example.testnosecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName applyrecord
 */
@TableName(value ="applyrecord")
@Data
public class Applyrecord implements Serializable {
    /**
     * 登记记录编号由（uid+applisubtime）用户id+申请提交时间组成
     */
    @TableId
    private String anumbering;

    public String getAnumbering() {
        return anumbering;
    }

    public void setAnumbering(String anumbering) {
        this.anumbering = anumbering;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAsex() {
        return asex;
    }

    public void setAsex(String asex) {
        this.asex = asex;
    }

    public String getAidnumber() {
        return aidnumber;
    }

    public void setAidnumber(String aidnumber) {
        this.aidnumber = aidnumber;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getAcar() {
        return acar;
    }

    public void setAcar(String acar) {
        this.acar = acar;
    }

    public Date getAentertime() {
        return aentertime;
    }

    public void setAentertime(Date aentertime) {
        this.aentertime = aentertime;
    }

    public Date getAouttime() {
        return aouttime;
    }

    public void setAouttime(Date aouttime) {
        this.aouttime = aouttime;
    }

    public Date getApplisubtime() {
        return applisubtime;
    }

    public void setApplisubtime(Date applisubtime) {
        this.applisubtime = applisubtime;
    }

    /**
     * 申请人姓名
     */
    private String aname;

    /**
     * 申请人性别
     */
    private String asex;

    /**
     * 申请人身份证号
     */
    private String aidnumber;

    private String adesc;

    public String getAdesc() {
        return adesc;
    }

    public void setAdesc(String adesc) {
        this.adesc = adesc;
    }

    /**
     * 申请人手机号
     */
    private String aphone;

    /**
     * 申请人车牌号（选填）
     */
    private String acar;

    public Integer getAstatus() {
        return astatus;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    private Integer astatus;

    /**
     * 预约进校时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date aentertime;

    /**
     * 预约离校时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date aouttime;

    /**
     * 申请提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date applisubtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
