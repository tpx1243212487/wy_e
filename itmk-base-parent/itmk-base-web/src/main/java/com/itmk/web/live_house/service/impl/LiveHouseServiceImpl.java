package com.itmk.web.live_house.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.live_house.entity.LiveHouse;
import com.itmk.web.live_house.mapper.LiveHouseMapper;
import com.itmk.web.live_house.service.LiveHouseService;
import org.springframework.stereotype.Service;

@Service
public class LiveHouseServiceImpl extends ServiceImpl<LiveHouseMapper, LiveHouse> implements LiveHouseService {
}
