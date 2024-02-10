package com.vivatech.notification.notificationmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String msisdn;
    private String messageTitle;
    @Column(columnDefinition = "text")
    private String messageBody;
    private String messageType;
    private String status;
    private String smsType;
    private int retry;
}
