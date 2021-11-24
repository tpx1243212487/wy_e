package com.itmk.web.live_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.service.FeeParkService;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.service.FeePowerService;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.service.FeeWaterService;
import com.itmk.web.live_park.entity.LivePark;
import com.itmk.web.live_user.entity.AssignHouseParm;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.entity.LiveUserParm;
import com.itmk.web.live_user.service.LiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业主控制器
 */
@RestController
@RequestMapping("/api/liveUser")
public class LiveUserController {
    @Autowired
    private LiveUserService liveUserService;
    @Autowired
    private FeeWaterService feeWaterService;
    @Autowired
    private FeePowerService feePowerService;
    @Autowired
    private FeeParkService feeParkService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 业主列表查询
     */

    @GetMapping("/list")
    public ResultVo getList(LiveUserParm liveUserParm){
        //构造分页对象
        IPage<LiveUser> page = new Page<>();
        page.setSize(liveUserParm.getPageSize());
        page.setCurrent(liveUserParm.getCurrentPage());
        IPage<LiveUser> list = liveUserService.getList(page, liveUserParm.getLoginName(), liveUserParm.getPhone());
        return ResultUtils.success("查询成功",list);
    }
    /**
     * 新增业主
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:liveUser:add')")
    public ResultVo add(@RequestBody LiveUser liveUser){
        //查询登录名是否被占用
        QueryWrapper<LiveUser> query = new QueryWrapper<>();
        query.lambda().eq(LiveUser::getUsername,liveUser.getUsername());
        LiveUser one = liveUserService.getOne(query);
        if(one != null){
            return ResultUtils.error("登录名被占用!");
        }
        //用户名需要加密
//        liveUser.setPassword(DigestUtils.md5DigestAsHex(liveUser.getPassword().getBytes()));
        liveUser.setPassword(passwordEncoder.encode(liveUser.getPassword()));
        liveUserService.saveLiveUser(liveUser);
        return ResultUtils.success("新增业主成功!");
    }

    /**
     * 编辑业主
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:liveUser:edit')")
    public ResultVo edit(@RequestBody LiveUser liveUser){
        //编辑判断登录名是否被占用
        //查询登录名是否被占用
        QueryWrapper<LiveUser> query = new QueryWrapper<>();
        query.lambda().eq(LiveUser::getUsername,liveUser.getUsername());
        LiveUser one = liveUserService.getOne(query);
        if(one != null && !one.getUserId().equals(liveUser.getUserId())){
            return ResultUtils.error("登录名被占用!");
        }
        liveUserService.editLiveUser(liveUser);
        return ResultUtils.success("编辑成功!");
    }
    /**
     * 编辑查询回显
     */
    @GetMapping("/getUserById")
    public ResultVo getUserById( LiveUser liveUser){
        LiveUser user = liveUserService.getUser(liveUser.getUserId());
        return ResultUtils.success("查询成功",user);
    }
    /**
     * 分配房屋保存
     */
    @PostMapping("/assignSave")
    @PreAuthorize("hasAuthority('sys:liveUser:assignHome')")
    public ResultVo assignSave(@RequestBody AssignHouseParm parm){
        liveUserService.assignHouse(parm);
        return ResultUtils.success("分配房屋成功!");
    }

    /**
     * 分配车位保存
     */
    @PostMapping("/assignParkSave")
    @PreAuthorize("hasAuthority('sys:liveUser:assignCar')")
    public ResultVo assignParkSave(@RequestBody LivePark livePark){
        liveUserService.assignSavePark(livePark);
        return ResultUtils.success("分配车位成功!");
    }

    /**
     * 退房：
     *      1.查询电费、水费是否已经交清；
     *     2.更新租户和房屋关系表状态为解绑；
     *     3.更新房屋表的使用状态为未使用；
     */
    @PostMapping("/returnHose")
    @PreAuthorize("hasAuthority('sys:liveUser:returnHome')")
    public ResultVo returnHose(@RequestBody AssignHouseParm parm){
        //1.查询电费、水费是否交清
        //构造查询条件
        QueryWrapper<FeeWater> queryWater = new QueryWrapper<>();
        queryWater.lambda().eq(FeeWater::getHouseId,parm.getHouseId())
                .eq(FeeWater::getUserId,parm.getUserId())
                .eq(FeeWater::getPayWaterStatus,"0");
        List<FeeWater> list = feeWaterService.list(queryWater);
        if(list != null && list.size() >0){
            return ResultUtils.error("请缴水费之后再退房!");
        }
        //查询电费
        QueryWrapper<FeePower> queryPower = new QueryWrapper<>();
        queryPower.lambda().eq(FeePower::getHouseId,parm.getHouseId())
                .eq(FeePower::getUserId,parm.getUserId())
                .eq(FeePower::getPayPowerStatus,"0");
        List<FeePower> list1 = feePowerService.list(queryPower);
        if(list1 != null && list1.size() >0){
            return ResultUtils.error("请缴电费之后再退房!");
        }
        liveUserService.returnHouse(parm);
        return ResultUtils.success("退房成功!");
    }

    /**
     * 退车位
     *      1.查询车位费是否已经交清；
     *     2.更新租户和车位的关系为解绑；
     *     3.更新车位的使用状态为未使用；
     *
     */
    @PreAuthorize("hasAuthority('sys:liveUser:returnCar')")
    @PostMapping("/returnPark")
    public ResultVo returnPark(@RequestBody LivePark livePark){
//        1.查询车位费是否已经交清；
        QueryWrapper<FeePark> query = new QueryWrapper<>();
        query.lambda().eq(FeePark::getParkId,livePark.getParkId())
                .eq(FeePark::getUserId,livePark.getUserId())
                .eq(FeePark::getPayParkStatus,"0");
        List<FeePark> list = feeParkService.list(query);
        if(list != null && list.size() >0){
            return ResultUtils.error("请缴清停车费后再退车位!");
        }
        liveUserService.returnPark(livePark);
        return ResultUtils.success("退车位成功!");
    }
}
