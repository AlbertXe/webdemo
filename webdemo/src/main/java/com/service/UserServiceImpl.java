package com.service;

import com.dao.cluster.UserDao;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlbertXe
 * @date 2019-11-05 10:52
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("clusterUserDao")
    private UserDao userDao;


    @Override
    public List<User> users() {
        List<User> user2List = new ArrayList<>();
        PageHelper.startPage(1, 5);
        List<User> users = userDao.getUsers();
        return users;
    }
}
