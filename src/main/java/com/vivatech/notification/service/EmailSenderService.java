package com.vivatech.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
  private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.bcc}")
    private String bccMail;



    public void simpleMailSender(String toEmail, String emailSubject, String emailBody) throws MessagingException {
      logger.info("Entering simpleMailSender");
      SimpleMailMessage mailmessage = new SimpleMailMessage();
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true );
      helper.setTo(toEmail);
      helper.setText(emailBody, true);
      helper.setSubject(emailSubject);
      helper.setFrom(fromEmail);
      helper.addBcc(bccMail);
      javaMailSender.send(message);
    }
}
