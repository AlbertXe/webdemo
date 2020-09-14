package com.config.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-09-12 12:04
 */
public class MyTransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor {
    private static final Object MANAGER_KEY = new Object();
    private final ConcurrentHashMap<Object, PlatformTransactionManager> cache = new ConcurrentHashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 为什么getThis
        Class<?> targetClass = AopUtils.getTargetClass(invocation.getThis());
        return invokeWithinTransaction(invocation.getMethod(), targetClass, invocation::proceed);
    }

    @Override
    protected PlatformTransactionManager determineTransactionManager(TransactionAttribute txAttr) {
        if (txAttr == null || getBeanFactory() == null) {
            return (PlatformTransactionManager) getTransactionManager();
        }
        String qualifier = txAttr.getQualifier();
        if (StringUtils.isNotBlank(qualifier)) {
            return determineQualifiedTransactionManager(super.getBeanFactory(), qualifier);
        } else if (StringUtils.isNotBlank(super.getTransactionManagerBeanName())) {
            // 父类的事务管理器
            return determineQualifiedTransactionManager(super.getBeanFactory(), super.getTransactionManagerBeanName());
        } else if (DataSourceHolder.get() != null) {//增加通过数据源对象获得对应事务管理器的方法
            return getTransactionManager((String) DataSourceHolder.get());
        } else {
            PlatformTransactionManager manager = (PlatformTransactionManager) getTransactionManager();
            if (manager == null) {
                manager = cache.get(MANAGER_KEY);
                if (manager == null) {
                    manager = super.getBeanFactory().getBean(PlatformTransactionManager.class);
                    cache.putIfAbsent(MANAGER_KEY, manager);
                }
            }
            return manager;
        }
    }

    private PlatformTransactionManager determineQualifiedTransactionManager(BeanFactory factory, String qualifier) {
        PlatformTransactionManager manager = cache.get(qualifier);
        if (manager == null) {
            manager = BeanFactoryAnnotationUtils.qualifiedBeanOfType(factory, PlatformTransactionManager.class, qualifier);
            cache.put(qualifier, manager);
        }
        return manager;
    }

    private PlatformTransactionManager getTransactionManager(String dataSourceName) {
        PlatformTransactionManager manager = null;
        BeanFactory beanFactory = super.getBeanFactory();
        DataSource dataSource = beanFactory.getBean(dataSourceName, DataSource.class);
        if (beanFactory instanceof ListableBeanFactory) {
            Map<String, PlatformTransactionManager> managerMap = ((ListableBeanFactory) beanFactory).getBeansOfType(PlatformTransactionManager.class);
            for (Map.Entry<String, PlatformTransactionManager> entry : managerMap.entrySet()) {
                if (entry.getValue() instanceof DataSourceTransactionManager) {
                    if (dataSource == ((DataSourceTransactionManager) entry.getValue()).getDataSource()) {
                        manager = entry.getValue();
                    }
                }
            }
        }
        return manager;
    }
}
