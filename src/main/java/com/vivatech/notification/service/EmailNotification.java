package com.vivatech.notification.service;


import com.vivatech.notification.dto.MessageEnvelope;
import com.vivatech.notification.dto.NotificationEnums;
import com.vivatech.notification.exception.UMSNotificationException;
import com.vivatech.notification.notificationmodel.Notifications;
import com.vivatech.model.ums.Student;
import com.vivatech.notification.repositories.NotificationsRepository;
import com.vivatech.repository.ums.StudentRepository;
import com.vivatech.notification.service.AbstractNotification;
import com.vivatech.notification.service.EmailSenderService;
import com.vivatech.notification.service.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Slf4j
@Component
public class EmailNotification extends AbstractNotification {
    @Autowired
    EmailSenderService emailService;
    @Autowired
    EmailTemplate emailTemplate;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private NotificationsRepository notificationRepository;

    @Override
    public boolean supports(MessageEnvelope envelope) {
        return envelope.getOperationType() == NotificationEnums.OperationType.APPROVAL
                || envelope.getOperationType() == NotificationEnums.OperationType.REGISTRATION;
    }

    @Override
    public void sendMessage(MessageEnvelope envelope) {

        if (envelope.getOperationType() == NotificationEnums.OperationType.APPROVAL){
            log.info("Sending {} Email", NotificationEnums.OperationType.APPROVAL.toString());
            Student student = studentRepository.findByAdmissionNo(envelope.getUserId());
            if (student == null) throw new UMSNotificationException("student not found");
            try {
                String emailTitle = "Registration Status in Admas University";
                String emailBody = emailTemplate.sendLoginDetailEmailToStudent(envelope);
                saveEmailNotification(student.getEmail(), emailTitle, emailBody);
            } catch (Exception e){
                log.error("Exception: ", e);
            }
        }

        if (envelope.getOperationType() == NotificationEnums.OperationType.REGISTRATION){
            log.info("Sending {} Email", NotificationEnums.OperationType.REGISTRATION.toString());
            Student student = studentRepository.findByAdmissionNo(envelope.getUserId());
            if (!StringUtils.isEmpty(student.getAdmissionNo())){
                try {
                    String emailTitle = "Registration Status in Admas University";
                    String emailBody = emailTemplate.sendRegistrationDetailToStudent(envelope);
                    saveEmailNotification(student.getEmail(), emailTitle, emailBody);
                } catch (Exception e){
                    log.error("Exception: ", e);
                }
            }
        }

    }


    public void saveEmailNotification(String emailId, String emailTitle, String emailBody){
        Notifications notifications = Notifications.builder()
                .msisdn(emailId)
                .messageTitle(emailTitle)
                .messageBody(emailBody)
                .messageType(NotificationEnums.MessageType.EMAIL.toString())
                .status("SUCCESS")
                .build();
        Notifications savedNotification = notificationRepository.save(notifications);
        sendEmail(savedNotification);
    }

    public void sendEmail(Notifications ele){
        try {
            emailService.simpleMailSender(ele.getMsisdn(), ele.getMessageTitle(), ele.getMessageBody());
        } catch (Exception e){
            log.error("Exception in sending email: ", e);
            ele.setStatus("FAILED");
            notificationRepository.save(ele);
        }
    }
}
