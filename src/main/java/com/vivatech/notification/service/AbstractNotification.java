package com.vivatech.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractNotification implements Notification {

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(String id, Object... args) {
        return messageSource.getMessage(id, args, Locale.getDefault());
    }

    public int getOrder() {
        return getRuleClassOrder(this.getClass());
    }

    private int getRuleClassOrder(Class<?> classz){
        int order = 1;
        Map<Class<?>, Integer> orderMap = new HashMap<>();
        orderMap.put(EmailNotification.class, 0);
        orderMap.put(SMSNotification.class, 0);
        order = orderMap.get(classz);
        return order;
    }
}
