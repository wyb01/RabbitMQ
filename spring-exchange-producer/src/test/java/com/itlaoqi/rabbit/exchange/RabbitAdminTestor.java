package com.itlaoqi.rabbit.exchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * description: 测试RabbitAdmin管理"交换机"和"队列"
 * @author: wyb
 * @createTime: 2020-01-05 22:43:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RabbitAdminTestor {

    @Resource(name="rabbitAdmin")
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * description: 测试创建"交换机"（三种）
     * @param :
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 16:30:01
     */
    @Test
    public void testCreateExchange(){
        //交换机名称 - 是否持久化 - 是否自动删除
        rabbitAdmin.declareExchange(new FanoutExchange("test.exchange.fanout" , true ,false));
        rabbitAdmin.declareExchange(new DirectExchange("test.exchange.direct" , true ,false));
        rabbitAdmin.declareExchange(new TopicExchange("test.exchange.topic" , true ,false));
    }

    /**
     * description: 测试"交换机"绑定"队列"
     * @param :
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 22:39:38
     */
    @Test
    public void testQueueAndBind(){
        rabbitAdmin.declareQueue(new Queue("test.queue"));  //创建队列
        rabbitAdmin.declareBinding(new Binding(     //绑定"交换机"和"队列"
                "test.queue", Binding.DestinationType.QUEUE,    //队列
                "test.exchange.topic", "#", new HashMap<String, Object>()   //交换机
        ));
        rabbitTemplate.convertAndSend("test.exchange.topic" , "abc" , "abc123");
    }

    /**
     * description: RabbitAdmin删除"交换机"和"队列"
     * @param :
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 22:45:06
     */
    @Test
    public void testDelete(){
        rabbitAdmin.deleteQueue("test.queue");   //删除队列
        rabbitAdmin.deleteExchange("test.exchange.fanout");  //删除交换机
        rabbitAdmin.deleteExchange("test.exchange.direct");  //删除交换机
        rabbitAdmin.deleteExchange("test.exchange.topic");   //删除交换机
    }

}
