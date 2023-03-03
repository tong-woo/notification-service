package com.tong.notification.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = "TestDirectQueue")
@Component
public class DirectRabbitConsumer {

    @RabbitHandler
    public void process(Map map, Channel channel, Message message) throws IOException {
        System.out.println("consumer get：" + map.toString());
        // manual ack needed
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
