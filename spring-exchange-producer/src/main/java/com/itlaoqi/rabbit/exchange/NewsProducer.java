package com.itlaoqi.rabbit.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/*@@请加Q群：369531466,与几百名工程师共同学习,遇到难题可随时@老齐,多一点真诚，少一点套路@@*/public class NewsProducer {
    private RabbitTemplate rabbitTemplate = null;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNews(String routingKey , News news){
        //convertAndSend 用于向exchange发送数据
        //第一个参数是routingkey
        //第二个参数是要传递的对象，可以是字符串、byte【】或者任何实现了【序列化接口】的对象
        rabbitTemplate.convertAndSend(routingKey , news);
        System.out.println("新闻发送成功");
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        NewsProducer np = (NewsProducer)ctx.getBean("newsProducer");
        np.sendNews("us.20190101" , new News("新华社" , "特朗普又又又退群啦" , new Date() , "国际新闻内容"));
        np.sendNews("china.20190101" , new News("凤凰TV" , "XXX企业荣登世界500强" , new Date() , "国内新闻内容"));
    }
}
