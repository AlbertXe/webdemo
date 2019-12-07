package com.aop.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  ServletContextListener：用来监听 ServletContext 属性的操作，比如新增、修改、删除。
 *  HttpSessionListener：用来监听 Web 应用种的 Session 对象，通常用于统计在线情况。
 *  ServletRequestListener：用来监听 Request 对象的属性操作。
 * @author AlbertXe
 * @date 2019-12-07 16:01
 */
@Log4j2
public class MyHttpSessionListener implements HttpSessionListener {
    private AtomicInteger count = new AtomicInteger();
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        count.incrementAndGet();
        se.getSession().getServletContext().setAttribute("count", count.get());
        log.info("在线人数为：{}", count.get());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        count.decrementAndGet();
        se.getSession().getServletContext().setAttribute("count", count.get());
        log.info("在线人数为：{}", count.get());
    }
}
