package com.dao.master;

import com.pojo.Book;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author AlbertXe
 * @date 2019-11-05 14:33
 */
@Component
public interface BookDao {
    Book selectById(long id);

    Map<String, Object> getCount(Map<String, Object> map);
}
