package com.vivatech.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageEnvelope {
    private String userId;
    private NotificationEnums.UserType userType;
    private String message;
    private NotificationEnums.OperationType operationType;
}
