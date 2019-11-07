package com.controller;

import com.dao.BookDao;
import com.github.pagehelper.PageInfo;
import com.pojo.Book;
import com.pojo.User2;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BookDao bookDao;

    @RequestMapping("/users")
    public List<User2> users() {
        List<User2> users = userService.users();
        PageInfo pageInfo = new PageInfo(users);

        long total = pageInfo.getTotal();
        logger.debug("total={}", total);
        System.out.println("total=" + total);

        Book book = bookDao.selectById(1);
        System.out.println(book);
        return users;
    }


}
