package com.interceptor;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;

/**
 * @description: 拦截器
 * @author: AlbertXe
 * @create: 2020-09-09 19:37
 */
@Getter
@Setter
public abstract class AbstractInterceptor implements Interceptor, Serializable {
    protected boolean showsql;

}
