package com.itmk.web.park_list.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.park_list.entity.ParkList;
import com.itmk.web.park_list.entity.ParkListParm;
import com.itmk.web.park_list.service.ParkListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车位管理模块控制器
 */
@RestController
@RequestMapping("/api/parkList")
public class ParkListController {
    @Autowired
    private ParkListService parkListService;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public ResultVo getList(ParkListParm parkListParm){
        IPage<ParkList> list = parkListService.getList(parkListParm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 新增车位
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:parkList:add')")
    public ResultVo add(@RequestBody ParkList parkList){
        boolean save = parkListService.save(parkList);
        if(save){
            return ResultUtils.success("新增车位成功!");
        }
        return ResultUtils.error("新增车位失败!");
    }

    /**
     * 编辑车位
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:parkList:edit')")
    public ResultVo edit(@RequestBody ParkList parkList){
        boolean save = parkListService.updateById(parkList);
        if(save){
            return ResultUtils.success("编辑车位成功!");
        }
        return ResultUtils.error("编辑车位失败!");
    }

    /**
     * 删除车位
     */
    @DeleteMapping("/{parkId}")
    @PreAuthorize("hasAuthority('sys:parkList:delete')")
    public ResultVo delete(@PathVariable("parkId") Long parkId){
        boolean b = parkListService.removeById(parkId);
        if(b){
            return ResultUtils.success("删除车位成功!");
        }
        return ResultUtils.error("删除车位失败!");
    }

    /**
     * 查询车位列表
     */
    @GetMapping("/listNoPage")
    public ResultVo getListNoPage(){
        List<ParkList> list = parkListService.list();
        return ResultUtils.success("查询成功",list);
    }
}
