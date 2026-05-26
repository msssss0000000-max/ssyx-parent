package com.atguigu.ssyx.mq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送消息的方法
    //exchange:交换机名称
    //routingKey:路由key
    //message:消息内容
    public boolean sendMessage(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
