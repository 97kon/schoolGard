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
 * @TableName noshow
 */
@TableName(value ="noshow")
@Data
public class Noshow implements Serializable {
    /**
     * 爽约表编号
     */
    @TableId(type = IdType.AUTO)
    private Integer nid;

    /**
     * 爽约人
     */
    private String nname;

    /**
     * 爽约的时间
     */
    private Date ndatetime;

    /**
     * 爽约记录的编号（关联apply表anumbering）
     */
    private String nanumbering;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}