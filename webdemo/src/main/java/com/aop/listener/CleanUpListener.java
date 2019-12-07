package com.aop.listener;

import com.github.pagehelper.PageHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author AlbertXe
 * @date 2019-11-26 16:25
 */
public class CleanUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        PageHelper.clearPage();
        System.out.println("调用Destroyed");
    }
}
