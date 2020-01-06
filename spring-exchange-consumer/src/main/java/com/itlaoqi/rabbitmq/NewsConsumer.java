package com.itlaoqi.rabbitmq;

import com.itlaoqi.rabbit.exchange.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * description: 消息消费者
 * @author: wyb
 * @createTime: 2020-01-05 15:17:31
 */
public class NewsConsumer {

    /**
     * description: 接收
     * @param news:
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 15:17:53
     */
    public void recv(News news){
        System.out.println("接收到最新新闻：" + news.getTitle() + ":" + news.getSource());
    }

    public static void main(String[] args) {
        //初始化IOC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    }
}
