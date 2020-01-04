package com.itlaoqi.rabbitmq.confirm;

import com.itlaoqi.rabbitmq.utils.RabbitConstant;
import com.itlaoqi.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * description:
 *  Confirm代表生产者将消息送到Broker时产生的状态，后续会出现两种情况：
 *      - ack 代表Broker已经将数据接收
 *      - nack 代表Broker拒收消息。原因有多种，队列已满，限流，IO异常。。。
 *   Return代表消息被Broker正常接收（ack）后，但Broker没有对应的队列进行投递时产生的状态，消息被退还给生产者。
 *   注意：上面两种状态只代表生产者与Broker之间消息投递的情况，与消费者是否接受/确认消息无关。
 *
 * @author: wyb
 * @createTime: 2020-01-04 14:36:48
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException, TimeoutException {
        Map area = new LinkedHashMap<String, String>();
        area.put("china.hebei.shijiazhuang.20991011", "中国河北石家庄20991011天气数据");
        area.put("china.shandong.qingdao.20991011", "中国山东青岛20991011天气数据");
        area.put("china.henan.zhengzhou.20991011", "中国河南郑州20991011天气数据");
        area.put("us.cal.la.20991011", "美国加州洛杉矶20991011天气数据");

        area.put("china.hebei.shijiazhuang.20991012", "中国河北石家庄20991012天气数据");
        area.put("china.shandong.qingdao.20991012", "中国山东青岛20991012天气数据");
        area.put("china.henan.zhengzhou.20991012", "中国河南郑州20991012天气数据");
        area.put("us.cal.la.20991012", "美国加州洛杉矶20991012天气数据");

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.confirmSelect();  //开启confirm监听模式
        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long l, boolean b) throws IOException {
                //第一个参数可以看作是当前消息的id，唯一标识
                //第二个参数代表接收的数据是否为批量接收，一般我们用不到。
                System.out.println("消息已被Broker接收,Tag:" + l);
            }

            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("消息已被Broker拒收,Tag:" + l);
            }
        });
        //如果消息无法正常投递则return回生产者
        channel.addReturnListener(new ReturnCallback() {
            public void handle(Return r) {
                System.err.println("===========================");
                System.err.println("Return编码：" + r.getReplyCode() + " - Return描述:" + r.getReplyText());
                System.err.println("交换机:" + r.getExchange() + " - 路由key:" + r.getRoutingKey() );
                System.err.println("Return主题：" + new String(r.getBody()));
                System.err.println("===========================");
            }
        });
        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> me = itr.next();
            //Routing key 第二个参数相当于数据筛选的条件
            //第三个参数为："mandatory" - true代表如果消息无法正常投递则return回生产者，如果false，则直接将消息放弃。
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC,me.getKey() ,true, null , me.getValue().getBytes());
        }

        /*channel.close();
        connection.close();*/
    }
}
