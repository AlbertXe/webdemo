package com.config;

import com.aop.interceptor.MyInterceptor;
import com.aop.listener.MyHttpSessionListener;
import com.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author AlbertXe
 * @date 2019-12-07 16:10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor interceptor;

    /**
     * 将拦截器加入拦截器链
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

    @Bean
    public ServletListenerRegistrationBean registrationBean() {
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(new MyHttpSessionListener());
        return registrationBean;
    }

    @Bean
    public ContextUtils contextUtils() {
        return new ContextUtils();
    }
}
