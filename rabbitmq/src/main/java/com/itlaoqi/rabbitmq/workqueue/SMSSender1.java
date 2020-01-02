package com.itlaoqi.rabbitmq.workqueue;

import com.itlaoqi.rabbitmq.utils.RabbitConstant;
import com.itlaoqi.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
* @Description: 模拟短信发送者1（如果是根据"轮询"或者"性能"进行消息处理时，使用该模式）
* @Author: wyb
* @Date: 2020-01-02 17:21:17
*/
public class SMSSender1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
        //如果不写basicQos（1），则自动MQ会将所有请求"平均"发送给所有消费者
        //basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），再从队列中获取一个新的
        channel.basicQos(1);  //处理完一个取一个(调用basicAck（）方法后)
        channel.basicConsume(RabbitConstant.QUEUE_SMS , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body); //处理接收到的短信（比如调用发送短信接口发送短信）
                System.out.println("SMSSender1-短信发送成功:" + jsonSMS);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
