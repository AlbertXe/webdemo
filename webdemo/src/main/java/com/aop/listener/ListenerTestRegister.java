package com.aop.listener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**
 * 注冊器測試
 * @author AlbertXe
 * @date 2019-11-26 15:56
 */
@Configuration
public class ListenerTestRegister {
    @Bean
    public ServletListenerRegistrationBean registerListener() {
        ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new CleanUpListener());
        servletListenerRegistrationBean.setOrder(1);
        return servletListenerRegistrationBean;
    }
}
