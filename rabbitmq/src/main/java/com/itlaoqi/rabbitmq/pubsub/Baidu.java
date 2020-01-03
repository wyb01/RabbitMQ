package com.itlaoqi.rabbitmq.pubsub;

import com.itlaoqi.rabbitmq.utils.RabbitConstant;
import com.itlaoqi.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 队列与交换机绑定,获取消息
 * @Author: wyb
 * @Date: 2020-01-02 18:46:05
 */
public class Baidu {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null); //创建队列
        // queueBind用于将"队列"与"交换机"绑定
            // 参数1：队列名
            // 参数2：交换机名
            // 参数三：路由key（暂时用不到)
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER, "");
        channel.basicQos(1); //确认后，再接受下一条消息
        channel.basicConsume(RabbitConstant.QUEUE_BAIDU , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
