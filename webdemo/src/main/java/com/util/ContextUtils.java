package com.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author AlbertXe
 * @date 2019-12-07 16:29
 */
public class ContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtils.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
