package com.itmk.web.user_complaint.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.user_complaint.entity.UserComplaint;
import com.itmk.web.user_complaint.mapper.UserComplaintMapper;
import com.itmk.web.user_complaint.service.UserComplaintService;
import org.springframework.stereotype.Service;

@Service
public class UserComplaintServiceImpl extends ServiceImpl<UserComplaintMapper, UserComplaint> implements UserComplaintService {
}
