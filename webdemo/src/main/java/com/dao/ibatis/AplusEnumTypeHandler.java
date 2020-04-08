package com.dao.ibatis;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * mybatis枚举映射扩展类
 *
 * @param <E>
 */
public class AplusEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    //枚举类
    private Class<E> type;
    public static final String GETVALUE = "getValue";

    public AplusEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    /**
     * 根据枚举名 确定要入库的值(code)
     *
     * @param e
     * @return
     */
    private String setEnum(String e) {
        //1. 正常情况添加枚举名到数据库. 这个情况是将getValue添加到数据库
        if (e == null) {
            return null;
        }
        if (!hasEnum(e)) {
            return e;
        }

        //特殊的枚举处理 实现了(EnumType), 添加枚举某个成员变量到数据库
        E[] es = type.getEnumConstants();//获得所有枚举
        String s;
        if (ArrayUtils.isEmpty(es)) {
            throw new RuntimeException("enum " + e + " not found");
        }
        for (E e1 : es) {
            if (Objects.equals(e, e1.name())) {
                try {
                    Method method = type.getMethod(GETVALUE);
                    s = String.valueOf(method.invoke(e1));
                    return s;
                } catch (ReflectiveOperationException e2) {
                }
            }
        }
        throw new RuntimeException("enum " + e + " not found");
    }

    /**
     * 根据数据存储的枚举信息 获取枚举对象
     *
     * @param e
     * @return
     */
    private E getEnum(String e) {
        //正常的枚举处理（通过枚举名获取枚举）
        if (e == null) {
            return null;
        }
        if (!hasEnum(e)) {
            return Enum.valueOf(type, e);
        }
        //特殊的枚举处理，（实现了EnumType，）通过枚举某个成员变量获取枚举
        E[] es = type.getEnumConstants();
        if (ArrayUtils.isEmpty(es)) {
            throw new RuntimeException("enum " + e + " not found");
        }
        for (E e1 : es) {
            try {
                Method method = type.getMethod(GETVALUE);
                String s = String.valueOf(method.invoke(e1));
                if (Objects.equals(e, s)) {
                    return e1;
                }
            } catch (Exception e2) {

            }
        }
        throw new RuntimeException("enum " + e + " not found");
    }

    /**
     * 判断枚举是否实现 EnumType 接口
     *
     * @param e
     * @return
     */
    private boolean hasEnum(String e) {
        //获得该枚举类 所实现的所有接口
        Class<?>[] classes = type.getInterfaces();
        if (ArrayUtils.isEmpty(classes)) {
            return false;
        }
        List<Class<?>> list = Arrays.asList(classes);
        if (!CollectionUtils.contains(list.iterator(), EnumType.class)) {
            return false;
        }
        return true;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            preparedStatement.setString(i, setEnum(e.name()));
        } else {
            preparedStatement.setObject(i, setEnum(e.name()), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String columnName = resultSet.getString(s);
        return getEnum(columnName);
    }


    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String s = resultSet.getString(i);
        return getEnum(s);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String s = callableStatement.getString(i);
        return getEnum(s);
    }
}
