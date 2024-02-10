package com.vivatech.notification.service;

import com.vivatech.notification.dto.MessageEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NotificationServer {

    @Autowired
    private SMSNotification smsNotification;

    @Autowired
    private EmailNotification emailNotification;

    protected void sendMessages(MessageEnvelope envelope){
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(smsNotification);
        notificationList.add(emailNotification);
        for (Notification notification : notificationList) {
            if (!notification.supports(envelope)){
                continue;
            }
            notification.sendMessage(envelope);
        }
    }
}
