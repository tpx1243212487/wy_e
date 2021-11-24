package com.itmk.web.house_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.house_building.entity.HouseBuilding;
import com.itmk.web.house_building.entity.HouseBuildingParm;

public interface HouseBuildingService extends IService<HouseBuilding> {
    //列表查询
    IPage<HouseBuilding> getList(HouseBuildingParm parm);
}
