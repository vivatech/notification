package com.vivatech.notification.dto;

public class NotificationEnums {
    public enum MessageType{
        SMS, EMAIL
    }

    public enum UserType {
        STUDENT, STAFF
    }

    public enum OperationType {
        REGISTRATION, APPROVAL, COURSE_OFFERING_UPDATE, SCHOLARSHIP_UPDATE
    }
}
