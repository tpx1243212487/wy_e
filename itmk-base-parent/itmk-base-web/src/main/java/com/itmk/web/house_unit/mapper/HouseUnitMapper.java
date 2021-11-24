package com.itmk.web.house_unit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.house_unit.entity.HouseUnit;
import org.apache.ibatis.annotations.Param;

public interface HouseUnitMapper extends BaseMapper<HouseUnit> {
    IPage<HouseUnit> getList(IPage<HouseUnit> page, @Param("unitName") String unitName,@Param("buildNme") String buildNme);
}
