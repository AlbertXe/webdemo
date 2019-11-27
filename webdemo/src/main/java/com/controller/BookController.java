package com.controller;

import com.dao.master.BookDao;
import com.pojo.Book;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AlbertXe
 * @date 2019-11-05 14:47
 */
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private SqlSession sqlSession;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book getBook(long id) {
        System.out.println("id=" + id);
        Book book = bookDao.selectById(Long.valueOf(id));
        System.out.println(book);
        return book;
    }

    /**
     * 根据id来汇总信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/book/{id}")
    public int getCount(@PathVariable("id") long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        Map<String, Object> countMap = bookDao.getCount(map);
        System.out.println("sumNum=" + countMap.get("sumNum"));
        System.out.println("idSum=" + countMap.get("idSum"));
        return 0;
    }

    /**
     * 根据id来汇总信息
     * 返回的List  [{idSum=49974900, sumNum=9799}]
     */
    public void getBookItem() {
        System.out.println("getBookItem");
        Map map = new HashMap();
        map.put("id", 200);
        List<Object> getCount = sqlSession.selectList("getCount", map);
        System.out.println(getCount);
    }
}
