package com.vivatech.notification.exception;

public class UMSNotificationException extends RuntimeException {

    public UMSNotificationException() {
        super("Not found"); // Provide a default error message
    }

    public UMSNotificationException(String message) {
        super(message); // Allow custom error messages
    }

}
