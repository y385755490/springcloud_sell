package com.ymd.user.service.impl;

import com.ymd.user.dataobject.UserInfo;
import com.ymd.user.repository.UserInfoRepository;
import com.ymd.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
