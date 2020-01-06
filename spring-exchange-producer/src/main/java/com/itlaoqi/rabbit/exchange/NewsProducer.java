package com.itlaoqi.rabbit.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * description:
 * @param null:
 * @return:
 * @author: wyb
 * @createTime: 2020-01-04 16:43:22
 */
public class NewsProducer {

    private RabbitTemplate rabbitTemplate = null;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * description: 发送新闻消息
     * @param routingKey
     * @param news:
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 13:35:51
     */
    public void sendNews(String routingKey , News news){
            //第一个参数是routingkey
            //第二个参数是要传递的对象，可以是"字符串"、"byte【】"或者任何"实现了【序列化接口】的对象"
        rabbitTemplate.convertAndSend(routingKey , news);  //convertAndSend 用于向exchange发送数据
        System.out.println("新闻发送成功");
    }

    /**
     * description: main（）方法
     * @param args:
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 13:36:16
     */
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        NewsProducer np = (NewsProducer)ctx.getBean("newsProducer");
        np.sendNews("us.20190101" , new News("新华社" , "特朗普又又又退群啦" , new Date() , "国际新闻内容"));
        np.sendNews("china.20190101" , new News("凤凰TV" , "XXX企业荣登世界500强" , new Date() , "国内新闻内容"));
    }
}
