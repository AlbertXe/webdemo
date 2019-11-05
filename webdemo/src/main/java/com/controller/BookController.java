package com.controller;

import com.dao.BookDao;
import com.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2019-11-05 14:47
 */
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book getBook(String id) {
        System.out.println("id=" + id);
        Book book = bookDao.selectById(Long.valueOf(id));
        System.out.println(book);
        return book;
    }
}
