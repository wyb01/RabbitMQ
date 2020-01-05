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

    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        /**
         * CorrelationData 消息的附加信息，即自定义id
         * isack 代表消息是否被broker（MQ）接收 true 代表接收 false代表拒收。
         * cause 如果拒收cause则说明拒收的原因，帮助我们进行后续处理
         */
        public void confirm(CorrelationData correlationData, boolean isack, String cause) {
            System.out.println(correlationData);
            System.out.println("ack:" + isack);
            if(isack == false){
                System.err.println(cause);
            }
        }
    };

    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingkey) {
            System.err.println("Code:" + replyCode + ",Text:" + replyText );
            System.err.println("Exchange:" + exchange + ",RoutingKey:" + routingkey );
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
        //CorrelationData对象的作用是作为消息的附加信息传递，通常我们用它来保存消息的自定义id
        CorrelationData cd = new CorrelationData(emp.getEmpno() + "-" + new Date().getTime());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //对象转换成"字节数组"并发送给MQ
        rabbitTemplate.convertAndSend("springboot-exchange" , "hr.employee" , emp , cd);
    }


}
