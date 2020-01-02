package com.itlaoqi.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
* @Description: 获取rabbitmq连接的工具类
* @Author: wyb
* @Date: 2020-01-02 11:35:53
*/
public class RabbitUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();  //ConnectionFactory用于创建MQ的物理连接
    static {   //放入静态块，随着类加载进行执行；保证全局有且只有一块
        connectionFactory.setHost("120.79.178.18");
        connectionFactory.setPort(5672);                //5672是RabbitMQ的默认端口号
        connectionFactory.setUsername("wyb");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/test");     //虚拟主机（数据库）
    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();  //TCP 物理连接
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
