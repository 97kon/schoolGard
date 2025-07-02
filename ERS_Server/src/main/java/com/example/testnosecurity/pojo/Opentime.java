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
 * @TableName opentime
 */
@TableName(value ="opentime")
@Data
public class Opentime implements Serializable {
    /**
     * 数据编号
     */
    @TableId(type = IdType.AUTO)
    private Integer oid;

    /**
     * 时间名称
     */
    private String oname;

    /**
     * 开始时间
     */
    private Date ostartdatetime;

    /**
     * 结束时间
     */
    private Date oenddatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}