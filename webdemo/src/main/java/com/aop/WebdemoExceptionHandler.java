package com.aop;

import com.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 异常处理器
 * @author AlbertXe
 * @date 2019-11-22 15:45
 */
@RestControllerAdvice
public class WebdemoExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebdemoExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        Response response = new Response();
        response.setCode("500");
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String msg = "";
        for (ObjectError allError : allErrors) {
            msg += allError.getDefaultMessage()+";";
        }
        logger.error(msg);
        response.setMsg(msg);
        return response;
    }
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        Response response = new Response();
        response.setCode("500");
        logger.error(e.getMessage());
        return response;
    }
}
