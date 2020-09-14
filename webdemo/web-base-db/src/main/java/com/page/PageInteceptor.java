package com.page;

import com.interceptor.AbstractInterceptor;
import com.page.support.PropertiesHelper;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 分页拦截器
 * @author: AlbertXe
 * @create: 2020-09-09 19:36
 */
public class PageInteceptor extends AbstractInterceptor {
    private String dialectClass;
    private boolean asyncTotalCount;
    private static ExecutorService pool;


    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];


        return null;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        PropertiesHelper helper = new PropertiesHelper(properties);
        dialectClass = helper.getRequiredString("dialectClass");
        asyncTotalCount = helper.getBoolean("asyncTotalCount", Boolean.FALSE);
        setPoolMaxSize(helper.getInt("poolMaxSize", 0));
    }

    /**
     * 线程池化
     *
     * @param size
     */
    public void setPoolMaxSize(int size) {
        if (size > 0) {
            ThreadFactory factory = new BasicThreadFactory.Builder().namingPattern("page-interceptor-thread-%n").build();
            pool = new ScheduledThreadPoolExecutor(size, factory);
        } else {
            pool = new ThreadPoolExecutor(size, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }
    }

    private <T> Future<T> call(Callable call, boolean async) {
        if (async) {
            return pool.submit(call);
        } else {
            FutureTask futureTask = new FutureTask(call);
            futureTask.run();
            return futureTask;
        }
    }
}
