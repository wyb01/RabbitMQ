package com.itlaoqi.rabbitmq.springboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * description: 消息生产者
 * @author: wyb
 * @createTime: 2020-01-05 23:01:42
 */
@Component
public class MessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate ;   //Spring封装的模板

    /**
     * @Description: 回调函数，是否接收
     * @param null:
     * @Return:
     * @Author: wyb
     * @Date: 2020-01-06 10:26:38
     */
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        /**
         * CorrelationData 消息的附加信息，即自定义id
         * isack 代表消息是否被broker（MQ）接收 true 代表接收 false代表拒收。
         * cause 如果拒收cause则说明拒收的原因，帮助我们进行后续处理
         */
        public void confirm(CorrelationData correlationData, boolean isack, String cause) {
            System.out.println(correlationData);   //消息的附加信息，即自定义id
            System.out.println("ack:" + isack);
            if(isack == false){                    //broker（MQ）拒收
                System.err.println(cause);         //拒收的原因
            }
        }
    };

    /**
     * @Description: 
     * @param null: 
     * @Return: 
     * @Author: wyb
     * @Date: 2020-01-06 11:32:11       
     */
    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingkey) {
            System.err.println("Code:" + replyCode + ",Text:" + replyText );
            System.err.println("Exchange:" + exchange + ",RoutingKey:" + routingkey );   //交换机+路由key
        }
    };

    /**
     * description: 发送消息
     * @param emp: 自定义的消息对象
     * @return: void
     * @author: wyb
     * @createTime: 2020-01-05 23:02:55
     */
    public void sendMsg(Employee emp){
        //CorrelationData对象的作用是作为消息的"附加信息"传递，通常我们用它来保存消息的自定义id(消息的唯一标识)
        CorrelationData cd = new CorrelationData(emp.getEmpno() + "-" + new Date().getTime());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //对象转换成"字节数组"并发送给MQ
        rabbitTemplate.convertAndSend("springboot-exchange" , "hr.employee" , emp , cd);
    }

}
