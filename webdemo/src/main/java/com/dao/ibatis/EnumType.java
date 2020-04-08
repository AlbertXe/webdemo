package com.dao.ibatis;

/**
 * 自定义枚举 要实现这个才可以映射数据库
 *
 * @param <T>
 */
public interface EnumType<T> {
    T getValue();
}
