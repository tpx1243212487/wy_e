package com.itmk.web.fee_water.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.entity.FeeWaterParm;

public interface FeeWaterService extends IService<FeeWater> {
    //查询列表
    IPage<FeeWater> getList(FeeWaterParm feeWaterParm);
}
