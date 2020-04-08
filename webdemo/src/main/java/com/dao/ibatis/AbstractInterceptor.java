package com.dao.ibatis;

import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;

public abstract class AbstractInterceptor implements Interceptor, Serializable {


    protected boolean showSql = false;

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }
}
