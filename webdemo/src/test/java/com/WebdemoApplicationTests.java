package com;

import com.dao.master.UserDao;
import com.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
class WebdemoApplicationTests {
    @Autowired
    UserDao userDao;

    @Autowired
    com.dao.cluster.UserDao clusterUserDao;

    /**
     * 事务测试
     */
    @Test
    @Transactional
    void contextLoads() {
        User user = new User(null, "xie");
        int i = userDao.insert(user);
        log.info("用户{}", user);
        int a = 1 / 0;
    }

    /**
     * 事务测试
     */
    @Test
    @Transactional(value = "clusterTransactionManager")
    void test1() {
        User user = new User(null, "xie");
        int i = clusterUserDao.insert(user);
        log.info("用户{}", user);
        int a = 1 / 0;
    }

    @Test
    @Rollback
    public void test() {

    }

}
