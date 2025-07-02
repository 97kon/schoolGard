package com.example.testnosecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户编号
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户姓名
     */
    private String uname;

    /**
     * 密码
     */
    private String upwd;

    /**
     * 用户性别
     */
    private String usex;

    /**
     * 用户身份证号
     */
    private String uidnumber;

    /**
     * 用户手机号
     */
    private String uphone;

    /**
     * 头像路径
     */
    private String upic;

    /**
     * 0：没进黑名单；1：进黑名单
     */
    private Integer blacklist;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 进入黑名单的时间（根据条件多少天移出黑名单）
     */
    private Date enblacklisttime;

    private String code;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUidnumber() {
        return uidnumber;
    }

    public void setUidnumber(String uidnumber) {
        this.uidnumber = uidnumber;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUpic() {
        return upic;
    }

    public void setUpic(String upic) {
        this.upic = upic;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEnblacklisttime() {
        return enblacklisttime;
    }

    public void setEnblacklisttime(Date enblacklisttime) {
        this.enblacklisttime = enblacklisttime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}