package com.itmk.web.house_building.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.house_building.entity.HouseBuilding;
import com.itmk.web.house_building.entity.HouseBuildingParm;
import com.itmk.web.house_building.service.HouseBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 栋数控制器
 */
@RestController
@RequestMapping("/api/HouseBuilding")
public class HouseBuildingController {
    @Autowired
    private HouseBuildingService houseBuildingService;

    /**
     * 表格列表
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public ResultVo list(HouseBuildingParm parm){
        IPage<HouseBuilding> list = houseBuildingService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 给单元查询列表
     * @param
     * @return
     */
    @GetMapping("/unitList")
    public ResultVo unitList(){
        List<HouseBuilding> list = houseBuildingService.list();
        return ResultUtils.success("查询成功",list);
    }
    /**
     * 新增栋数
     * @param houseBuilding
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:houseBuilding:add')")
    public ResultVo save(@RequestBody HouseBuilding houseBuilding){
        boolean save = houseBuildingService.save(houseBuilding);
        if(save){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    /**
     * 编辑栋数
     * @param houseBuilding
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:houseBuilding:edit')")
    public ResultVo edit(@RequestBody HouseBuilding houseBuilding){
        boolean save = houseBuildingService.updateById(houseBuilding);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    /**
     * 删除栋数
     * @param buildId
     * @return
     */
    @DeleteMapping("/{buildId}")
    @PreAuthorize("hasAuthority('sys:houseBuilding:delete')")
    public ResultVo delete(@PathVariable("buildId") Long buildId){
        boolean b = houseBuildingService.removeById(buildId);
        if(b){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
}
