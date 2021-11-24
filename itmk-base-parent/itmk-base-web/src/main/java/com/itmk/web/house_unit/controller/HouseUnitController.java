package com.itmk.web.house_unit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.house_unit.entity.HouseUnit;
import com.itmk.web.house_unit.entity.HouseUnitParm;
import com.itmk.web.house_unit.service.HouseUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房屋单元控制器
 */
@RestController
@RequestMapping("/api/houseUnit")
public class HouseUnitController {
    @Autowired
    private HouseUnitService houseUnitService;

    /**
     * 获取单元列表
     */
    @GetMapping("/list")
    public ResultVo getList(HouseUnitParm houseUnitParm){
        IPage<HouseUnit> list = houseUnitService.getList(houseUnitParm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 单元新增
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:houseUnit:add')")
    public ResultVo add(@RequestBody HouseUnit houseUnit){
        boolean save = houseUnitService.save(houseUnit);
        if(save){
            return ResultUtils.success("新增单元成功!");
        }
        return ResultUtils.error("新增单元失败!");
    }

    /**
     * 单元编辑
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:houseUnit:edit')")
    public ResultVo edit(@RequestBody HouseUnit houseUnit){
        boolean save = houseUnitService.updateById(houseUnit);
        if(save){
            return ResultUtils.success("编辑单元成功!");
        }
        return ResultUtils.error("编辑单元失败!");
    }

    /**
     * 删除单元
     */
    @DeleteMapping("/{unitId}")
    @PreAuthorize("hasAuthority('sys:houseUnit:delete')")
    public ResultVo delete(@PathVariable("unitId") Long unitId){
        boolean b = houseUnitService.removeById(unitId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }
    /**
     * 根据栋数id查询单元列表
     */
    @GetMapping("/getUnitListByBuildId")
    public ResultVo getUnitListByBuildId(HouseUnit houseUnit){
        //构造查询条件
        QueryWrapper<HouseUnit> query = new QueryWrapper<>();
        query.lambda().eq(HouseUnit::getBuildId,houseUnit.getBuildId());
        List<HouseUnit> list = houseUnitService.list(query);
        return ResultUtils.success("查询成功",list);
    }
}
