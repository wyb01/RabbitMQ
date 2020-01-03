package com.itlaoqi.rabbitmq.routing;

import com.itlaoqi.rabbitmq.utils.RabbitConstant;
import com.itlaoqi.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * description:
 *  路由（Routing）模式，是在发布/订阅模式基础上的变种
 *  发布订阅模式是无条件将所有消息分发给所有消费者队列
 *  路由模式则是交换机根据Routing Key有条件的将数据筛选后发给消费者队列
 *  路由模式下交换机的类型被称为direct
 * @author: wyb
 * @createTime: 2020-01-03 22:16:00
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
        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> me = itr.next();
            //Routing key 第二个参数相当于数据筛选的条件
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_ROUTING , me.getKey() , null , me.getValue().getBytes());
        }

        channel.close();
        connection.close();
    }
}
