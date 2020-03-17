package com.zbl.service;

import com.zbl.pojo.User;

import java.util.List;

/**
 * @Author: zbl
 * @Date: 17:40 2020/3/1
 * @Description:
 */
public interface UserService {

    public User queryUserByName(String name);

    List<User> findAll();
}
