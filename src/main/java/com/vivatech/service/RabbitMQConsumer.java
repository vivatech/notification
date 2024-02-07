package com.vivatech.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivatech.MessageEnvelope;
import com.vivatech.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = "${rabbit.mq.queue.name}")
    public void consumeMessage(String envelope){
        try{
            log.info("Consumed Message: {}", envelope);
            MessageEnvelope message = new ObjectMapper().readValue(envelope, MessageEnvelope.class);
        } catch (Exception e){
            log.error("Exception: ", e);
        }
    }
}
