package com.dao.cluster;

import com.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("clusterUserDao")
public interface UserDao {

    @Select("select * from user")
    List<User> getUsers();
    @Select("select * from user where id=#{id}")
    User getUser(String id);
}
