package com.aop.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 过滤器
 * @author AlbertXe
 * @date 2019-12-07 13:55
 */
@Log4j2
@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化过滤器：MyFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);

        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        log.info("*****uri:{},url:{}*****",uri,url);
        if (uri.contains("/login")) {//做了过滤，跳转
            responseWrapper.sendRedirect("/login");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        log.info("销毁过滤器：MyFilter");
    }
}
