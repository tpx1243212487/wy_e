package com.itmk.web.house_building.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class HouseBuildingParm implements Serializable {
    private String name;
    private String type;
    //页容量
    private Long pageSize;
    //当前页
    private Long currentPage;
}
