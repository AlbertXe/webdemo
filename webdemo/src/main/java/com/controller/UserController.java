package com.controller;

import com.dao.UserDao;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 * @date 2019-10-30 19:59
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/users")
    public List<User> users() {
        System.out.println("users");

        PageHelper.startPage(1, 5);
        List<User> users = userDao.getUsers();
//        PageInfo pageInfo = new PageInfo(users);
//        List list = pageInfo.getList();

        return users;
    }
}
