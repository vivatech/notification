package com.vivatech.notification.service;

import com.vivatech.notification.dto.MessageEnvelope;
import com.vivatech.notification.dto.NotificationEnums;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SMSNotification extends AbstractNotification{

//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private SMSService smsService;

    @Override
    public boolean supports(MessageEnvelope envelope) {
        return envelope.getOperationType() == NotificationEnums.OperationType.REGISTRATION;
    }

    @Override
    public void sendMessage(MessageEnvelope envelope) {
//        if (envelope.getOperationType() == NotificationEnums.OperationType.REGISTRATION){
//            Student student = studentRepository.findByAdmissionNo(envelope.getUserId());
//            log.info("Sending registration sms message to Student {}", Utility.makeDtoToJson(student));
//            if (student == null) throw new UMSNotificationException("Student not found");
//            saveSMSNotification(student.getContactNo(), envelope.getMessage(), NotificationEnums.OperationType.REGISTRATION.toString());
//        }
        log.info("Hello");

    }

//    public void saveSMSNotification(String contactNo, String smsBody, String smsType){
//        Notifications notifications = Notifications.builder()
//                .msisdn(contactNo)
//                .messageBody(smsBody)
//                .messageType(NotificationEnums.MessageType.SMS.toString())
//                .smsType(smsType)
//                .status("SUCCESS")
//                .build();
//        Notifications savedNotification = notificationRepository.save(notifications);
//        sendSMS(savedNotification, contactNo);
//    }
//
//    private void sendSMS(Notifications ele, String msisdn) {
//        try {
//            log.info("Phone number {} is valid, starting to send sms", msisdn);
//            smsService.sendMessageToUsers(msisdn, ele.getMessageBody());
//            log.info("SMS sent successfully");
//        } catch (Exception e){
//            log.error("Exception: ", e);
//            ele.setStatus("FAILED");
//            notificationRepository.save(ele);
//        }
//    }
}
