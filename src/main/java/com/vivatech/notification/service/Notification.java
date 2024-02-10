package com.vivatech.notification.service;


import com.vivatech.notification.dto.MessageEnvelope;

public interface Notification {

    boolean supports(MessageEnvelope envelope);

    void sendMessage(MessageEnvelope envelope);

}
