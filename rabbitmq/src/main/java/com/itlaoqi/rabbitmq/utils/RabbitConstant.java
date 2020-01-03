package com.itlaoqi.rabbitmq.utils;

/**
* @Description: 常量工具类
* @Author: wyb
* @Date: 2020-01-02 14:28:41
*/
public class RabbitConstant {
    public static final String QUEUE_HELLOWORLD = "helloworld";   //队列
    public static final String QUEUE_SMS = "sms";                 //"workqueue"短信队列名称
    public static final String EXCHANGE_WEATHER = "weather";      //"发布/订阅"模式 - 交换机
    public static final String EXCHANGE_WEATHER_ROUTING = "weather_routing";  //"路由"模式 交换机
    public static final String QUEUE_BAIDU = "baidu";    //队列名称
    public static final String QUEUE_SINA = "sina";      //队列名称
    public static final String EXCHANGE_WEATHER_TOPIC = "weather_topic";
}
