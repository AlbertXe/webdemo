package com.dao.ibatis;

import com.pojo.User;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * insert update时候给特定字段默认值
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class BoundDefaultValueInterceptor extends AbstractInterceptor {
    private boolean enabledDsu = true;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];

        //当前sql 非insert update直接返回
        if (ms.getSqlCommandType() != SqlCommandType.INSERT && ms.getSqlCommandType() != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        String name = "default";
        //地下就可以做些赋值
        if (param instanceof User) {
            boundValue(ms, name, (User) param);
        }

        return invocation.proceed();
    }

    private void boundValue(MappedStatement ms, String name, User param) {
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            param.setName(name);
        }
    }

    /**
     * 将插件包装
     *
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public boolean isEnabledDsu() {
        return enabledDsu;
    }

    public void setEnabledDsu(boolean enabledDsu) {
        this.enabledDsu = enabledDsu;
    }
}
