package com.ymd.user.service;

import com.ymd.user.dataobject.UserInfo;

public interface UserService {

    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
