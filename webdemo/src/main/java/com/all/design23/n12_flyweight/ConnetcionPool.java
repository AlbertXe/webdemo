package com.all.design23.n12_flyweight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 享元模式
 * 目的是实现对象的共享，即共享池，当系统中对象多的时候可以减少内存的开销，通常与工厂模式一起使用
 * <p>
 * FlyWeightFactory负责创建和管理享元单元，当一个客户端请求时，工厂需要检查当前对象池中是否有符合条件的对象，
 * 如果有，就返回已经存在的对象，如果没有，则创建一个新对象，FlyWeight是超类
 */
public class ConnetcionPool {
    private Vector<Connection> pool;

    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "root";
    private String driverClassname = "com.mysql.jdbc.Driver";
    private int poolSize = 10;

    private static ConnetcionPool instance = null;
    Connection conn;

    private ConnetcionPool() {
        pool = new Vector<>(10);
        for (int i = 0; i < pool.size(); i++) {
            try {
                Class.forName(driverClassname);
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            pool.add(conn);
        }
    }

    public synchronized void release() {
        pool.add(conn);
    }


    public synchronized Connection getConnection() {
        if (pool.size() > 0) {

            return pool.get(0);
        } else {
            return null;
        }
    }


}
