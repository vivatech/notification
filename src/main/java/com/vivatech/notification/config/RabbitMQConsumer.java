package com.vivatech.notification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivatech.notification.dto.MessageEnvelope;
import com.vivatech.notification.service.NotificationServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer extends NotificationServer {

    @RabbitListener(queues = "${rabbit.mq.queue.name}")
    public void consumeMessage(String envelope){
        try{
            log.info("Consumed Message: {}", envelope);
            MessageEnvelope message = new ObjectMapper().readValue(envelope, MessageEnvelope.class);
            sendMessages(message);
        } catch (Exception e){
            log.error("Exception: ", e);
        }
    }
}
