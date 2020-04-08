package com.dao.ibatis;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlJsonHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(o));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JSON.parse(resultSet.getString(s));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSON.parse(resultSet.getString(i));
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSON.parse(callableStatement.getString(i));
    }
}
