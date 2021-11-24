package com.itmk.web.house_list.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.entity.ListParm;

public interface HouseListService extends IService<HouseList> {
    IPage<HouseList> getList(ListParm parm);
}
