package com.itlaoqi.rabbitmq;

import com.itlaoqi.rabbit.exchange.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*@@请加Q群：369531466,与几百名工程师共同学习,遇到难题可随时@老齐,多一点真诚，少一点套路@@*/public class NewsConsumer {
    public void recv(News news){
        System.out.println("接收到最新新闻：" + news.getTitle() + ":" + news.getSource());
    }

    public static void main(String[] args) {
        //初始化IOC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


    }
}
