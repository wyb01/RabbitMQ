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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
/*@@请加Q群：369531466,与几百名工程师共同学习,遇到难题可随时@老齐,多一点真诚，少一点套路@@*/public class RabbitAdminTestor {
    @Resource(name="rabbitAdmin")
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testCreateExchange(){
        rabbitAdmin.declareExchange(new FanoutExchange("test.exchange.fanout" , true ,false));
        rabbitAdmin.declareExchange(new DirectExchange("test.exchange.direct" , true ,false));
        rabbitAdmin.declareExchange(new TopicExchange("test.exchange.topic" , true ,false));
    }

    @Test
    public void testQueueAndBind(){
        rabbitAdmin.declareQueue(new Queue("test.queue"));
        rabbitAdmin.declareBinding(new Binding(
                "test.queue", Binding.DestinationType.QUEUE,
                "test.exchange.topic", "#", new HashMap<String, Object>()
        ));
        rabbitTemplate.convertAndSend("test.exchange.topic" , "abc" , "abc123");
    }

    @Test
    public void testDelete(){
        rabbitAdmin.deleteQueue("test.queue");
        rabbitAdmin.deleteExchange("test.exchange.fanout");
        rabbitAdmin.deleteExchange("test.exchange.direct");
        rabbitAdmin.deleteExchange("test.exchange.topic");
    }

}
