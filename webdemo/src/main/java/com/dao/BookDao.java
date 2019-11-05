package com.dao;

import com.pojo.Book;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2019-11-05 14:33
 */
@Component
public interface BookDao {
    Book selectById(long id);
}
