package com.itmk.web.house_list.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.entity.ListParm;
import com.itmk.web.house_list.mapper.HouseListMapper;
import com.itmk.web.house_list.service.HouseListService;
import org.springframework.stereotype.Service;

@Service
public class HouseListServiceImpl extends ServiceImpl<HouseListMapper, HouseList> implements HouseListService {
    @Override
    public IPage<HouseList> getList(ListParm parm) {
        //构造分页对象
        IPage<HouseList> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.getList(page,parm.getBuildNme(),parm.getUnitName(),parm.getHouseNum(),parm.getStatus());
    }
}
