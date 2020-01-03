package com.itlaoqi.rabbitmq.pubsub;

import com.itlaoqi.rabbitmq.utils.RabbitConstant;
import com.itlaoqi.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

    /**
     * @Description: "发布/订阅"模式 - 所有消费者获得相同的消息，特别适合"数据提供商与应用商"
     * "发布/订阅"模式中，交换机将无差别的将所有消息送入与之绑定的队列，
     * 所有消费者拿到的消息完全相同，交换机的类型被称为fanout
     * @Author: wyb
     * @Date: 2020-01-02 18:20:12
     */
public class WeatherBureau {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        String input = new Scanner(System.in).next();
        Channel channel = connection.createChannel();
        // 参数1 ：交换机
        // 作为信息提供者，不需要和队列进行交互，只需指定交换机即可
        // 信息发布到交换机
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"" , null , input.getBytes());
        channel.close();
        connection.close();
    }
}
