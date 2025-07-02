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
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 管理员账号
     */
    private String adusername;

    /**
     * 管理员密码
     */
    private String adpwd;

    /**
     * 创建时间
     */
    private Date adcreatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}