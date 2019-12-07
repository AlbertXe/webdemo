package com.aop.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器是动态拦截 action 调用的对象，然后提供了可以在 action 执行前后增加一些操作，
 * 也可以在 action 执行前停止操作，功能与过滤器类似，但是标准和实现方式不同
 * @author AlbertXe
 * @date 2019-12-07 14:06
 */
@Log4j2
@Component
public class MyInterceptor implements HandlerInterceptor {

    /**
     * controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("MyInterceptor拦截器调用：{}",requestURI);
        request.setAttribute("start", System.currentTimeMillis());
        return true;
    }

    /**
     * controller完成后，生成视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getRequestURI().contains("login")) {
            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("name");
            if ("haixiang".equals(name)) {
                log.info("[MyInterceptor]当前浏览器存在session:{}",name);
            }
        }
        log.info("[MyInterceptor]拦截器处理路径：{}",request.getRequestURI());
    }

    /**
     * 请求完全完毕后。做一些耗时统计
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long time = System.currentTimeMillis() -(long) request.getAttribute("start");
        log.info("[MyInterceptor]调用{}，耗时{}",request.getRequestURI(), time);
    }
}
