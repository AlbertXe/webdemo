package com.service;

import com.dao.UserDao;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import com.pojo.User2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019-11-05 10:52
 */
@Service
public class UserSericeImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public List<User2> users() {
        List<User2> user2List = new ArrayList<>();
        PageHelper.startPage(1, 5);
        List<User> users = userDao.getUsers();
        for (User user : users) {

            User2 user2 = new User2();
            user2.setId(user.getId());
            user2List.add(user2);
        }
        return user2List;
    }
}
