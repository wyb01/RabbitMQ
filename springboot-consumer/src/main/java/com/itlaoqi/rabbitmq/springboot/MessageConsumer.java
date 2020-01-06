package com.itlaoqi.rabbitmq.springboot;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Description: 接收消息
 * @Author: wyb
 * @Date: 2020-01-06 14:35:01
 */
@Component
public class MessageConsumer {

    /**
     * @Description: 用于接收消息的方法 - 这个方法运行后将处于等待的状态，有新的消息进来就会自动触发下面的方法处理消息
     *      @Payload 代表运行时将消息"反序列化"后注入到后面的参数中
     * @param employee:
     * @param channel:
     * @param headers:
     * @Return: void
     * @Author: wyb
     * @Date: 2020-01-06 14:32:57
     */
    //@RabbitListener注解用于声明式定义消息接收的"队列"与"exhcange"绑定的信息
    //在SpringBoot中，消费者这端使用"注解"获取消息
    //消费者端可以自动创建交换机和队列
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value="springboot-queue" , durable="true"),
                    exchange = @Exchange(value = "springboot-exchange" , durable = "true" , type = "topic") ,
                    key = "#"
            )
    )
    @RabbitHandler  //通知SpringBoot下面的方法用于接收消息
    public void handleMessage(@Payload Employee employee , Channel channel , @Headers Map<String,Object> headers) {
        System.out.println("=========================================");
        System.out.println("接收到" + employee.getEmpno() + ":" + employee.getName());

        Long tag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            //参数1：tag
            //参数2：是否批量接收
            channel.basicAck(tag , false);  //所有消息处理后必须进行消息的ack(确认签收)，channel.basicAck()
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=========================================");
    }
}
