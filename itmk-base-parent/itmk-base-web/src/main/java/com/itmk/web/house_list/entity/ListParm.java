package com.itmk.web.house_list.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListParm implements Serializable {
    //栋数名称
    private String buildNme;
    private String status;
    //单元名称
    private String unitName;
    private String houseNum;
    private Long currentPage;
    private Long pageSize;
}