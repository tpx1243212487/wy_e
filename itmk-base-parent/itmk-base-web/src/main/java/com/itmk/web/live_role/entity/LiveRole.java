package com.itmk.web.live_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 业主和角色的关联表
 */
@Data
@TableName("live_role")
public class LiveRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long livRoleId;
    private Long roleId;
    private Long userId;

}
