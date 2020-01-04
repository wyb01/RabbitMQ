package com.itlaoqi.rabbitmq.topic;

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
 * 主题Topic模式是在Routing模式基础上，提供了对RouteKey模糊匹配的功能，可以简化程序的编写。
 * 主题模式下，模糊匹配表达式规则为：
 *   * 匹配单个关键字
 *   # 匹配所有关键字
 *   主题模式下交换机的类型被称为topic
 * @param∂
 * @author: wyb
 * @createTime: 2020-01-04 14:13:15
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
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC,me.getKey() , null , me.getValue().getBytes());
        }

        channel.close();
        connection.close();
    }
}
