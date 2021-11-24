package com.itmk.web.house_building.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 栋数实体
 */
@Data
@TableName("house_building")
public class HouseBuilding implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long buildId;
    //0：普通房 1：电梯房
    private String type;
    //栋数名称
    private String name;
    //序号
    private Long orderNum;
}
