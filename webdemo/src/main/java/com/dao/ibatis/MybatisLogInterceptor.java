package com.dao.ibatis;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisLogInterceptor extends AbstractInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];
        BoundSql boundSql = ms.getSqlSource().getBoundSql(param);

        if (showSql) {
            String sql = boundSql.getSql();
            StringBuffer sb = new StringBuffer();
            sb.append("sql=").append(sql)
                    .append("id=").append(ms.getId())
                    .append("mapper file=").append(ms.getResource())
                    .append("param=").append(JSON.toJSONString(param))
                    .append("param class=").append(param == null ? null : JSON.toJSONString(param));
        }

        //添加CAT埋点
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
