package com.xhl.example.common.service;

import com.xhl.example.common.model.User;

public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);


    /**
     * 新方法- 获取数字
     */
    default Integer getNumber() {

        return 1;
    }

    default String getName() {
        return "xhl";
    }


}
