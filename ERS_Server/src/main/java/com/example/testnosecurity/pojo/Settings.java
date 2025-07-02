package com.example.testnosecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName settings
 */
@TableName(value ="settings")
@Data
public class Settings implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 迟到多少分钟算爽约
     */
    private Integer late;

    /**
     * 爽约多少次进入黑名单
     */
    private Integer noshowtimes;

    /**
     * 黑名单保持多少天
     */
    private Integer limitdays;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}