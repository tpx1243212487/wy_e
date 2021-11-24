package com.itmk.web.fee_park.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.entity.FeeParkParm;
import com.itmk.web.fee_park.service.FeeParkService;
import com.itmk.web.live_park.entity.LivePark;
import com.itmk.web.live_park.mapper.LiveParkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 停车费模块
 */
@RestController
@RequestMapping("/api/feePark")
public class FeeParkController {
    @Autowired
    private FeeParkService feeParkService;
    @Resource
    private LiveParkMapper liveParkMapper;
    /**
     * 我的停车费
     */
    @GetMapping("/getMyParkFee")
    public ResultVo getMyParkFee(FeeParkParm feeParkParm){
        //构造分页对象
        IPage<FeePark> page = new Page<>();
        page.setCurrent(feeParkParm.getCurrentPage());
        page.setSize(feeParkParm.getPageSize());
        //查询条件
        QueryWrapper<FeePark> query = new QueryWrapper<>();
        query.lambda().eq(FeePark::getUserId,feeParkParm.getUserId());
        IPage<FeePark> list = feeParkService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 新增：
     * 1.查询当前正在使用车位的用户
     * 2.入库
     */
    @PreAuthorize("hasAuthority('sys:feePark:add')")
    @PostMapping
    public ResultVo add(@RequestBody FeePark feePark){
        //1.查询当前正在使用车位的租户
        //构造查询条件
        QueryWrapper<LivePark> query = new QueryWrapper<>();
        query.lambda().eq(LivePark::getParkId,feePark.getParkId())
                .eq(LivePark::getLiveStatue,"0");
        LivePark livePark = liveParkMapper.selectOne(query);
        if(livePark == null){
            return ResultUtils.error("该车位暂无人员使用!");
        }
        feePark.setUserId(livePark.getUserId());
        //2.入库
        boolean save = feeParkService.save(feePark);
        if(save){
            return ResultUtils.success("新增停车费成功!");
        }
        return ResultUtils.error("新增停车费失败!");
    }

    /**
     * 编辑：1.查询当前正在使用的用户
     *       2.更新
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:feePark:edit')")
    public ResultVo edit(@RequestBody FeePark feePark){
        //1.查询当前正在使用车位的租户
        //构造查询条件
        QueryWrapper<LivePark> query = new QueryWrapper<>();
        query.lambda().eq(LivePark::getParkId,feePark.getParkId())
                .eq(LivePark::getLiveStatue,"0");
        LivePark livePark = liveParkMapper.selectOne(query);
        if(livePark == null){
            return ResultUtils.error("该车位暂无人员使用!");
        }
        feePark.setUserId(livePark.getUserId());
        //2.更新
        boolean b = feeParkService.updateById(feePark);
        if(b){
            return ResultUtils.success("编辑停车费成功!");
        }
        return ResultUtils.error("编辑停车费失败!");
    }

    /**
     * 删除： 1.判断是否已经缴费，如果缴费，不能删除
     *        2.删除
     */
    @PreAuthorize("hasAuthority('sys:feePark:delete')")
    @DeleteMapping("/{parkFeeId}")
    public ResultVo delete(@PathVariable("parkFeeId") Long parkFeeId){
        //1.判断是否已经缴费，根据id查询缴费状态
        QueryWrapper<FeePark> query = new QueryWrapper<>();
        query.lambda().eq(FeePark::getPayParkStatus,"1")
        .eq(FeePark::getParkFeeId,parkFeeId);
        FeePark one = feeParkService.getOne(query);
        if(one != null){
            return ResultUtils.error("已缴费，不能删除该数据!");
        }
        boolean b = feeParkService.removeById(parkFeeId);
        if(b){
            return ResultUtils.success("删除停车费成功!");
        }
        return ResultUtils.error("删除停车费失败!");
    }

    /**
     * 缴费：
     *   把当前这条数据的 payParkStatus 设为1
     */
    @PostMapping("/pay")
    public ResultVo pay(@RequestBody FeePark feePark){
        //把状态设为1
        feePark.setPayParkStatus("1");
        boolean b = feeParkService.updateById(feePark);
        if(b){
            return ResultUtils.success("缴费成功!");
        }
        return ResultUtils.error("缴费失败!");
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResultVo getList(FeeParkParm feeParkParm){
        IPage<FeePark> list = feeParkService.getList(feeParkParm);
        return ResultUtils.success("查询成功",list);
    }
}
