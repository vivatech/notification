package com.vivatech.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivatech.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class SMSService {
  private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

  @Value("${sms.url}")
  public String smsUrl;

  @Value("${sms.authorization}")
  public String smsAuth;

  @Value("${sms.smssender}")
  public String smsSender;

  public void sendMessageToUsers(String number, String text) throws IOException {
    logger.info("Entering sendMessageToUsers with Mobile : " + number);
    try {
      String smsMsgUrl = smsUrl;
      MessageRequest req = new MessageRequest();
      req.setFromNumber(smsSender);
      req.setToNumber(number);
      req.setMessage(text);

      ObjectMapper objMapper = new ObjectMapper();
      String jsonInputString = objMapper.writeValueAsString(req);

      logger.info("SMS Request : " + smsMsgUrl + ", Request Body : " + jsonInputString);

      URL url = new URL(smsMsgUrl);// your url i.e fetch data from .
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Accept", "/");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", "Basic " + smsAuth);

      conn.setDoOutput(true);
      // Setting request body
      try(OutputStream os = conn.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }
      logger.info("SMS Response code: " + conn.getResponseCode());

      if (conn.getResponseCode() != 202) {
          throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
      }
      else {
        logger.info("Sending SMS...");
      }
      InputStreamReader in = new InputStreamReader(conn.getInputStream());
      BufferedReader br = new BufferedReader(in);
      String output;
      while ((output = br.readLine()) != null) {
        logger.info("SMS Response : " + output);
      }
      conn.disconnect();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new IOException("MessageService::sendMessageToUsers() Exception : " + e.getLocalizedMessage());
    } finally {
      logger.info("Exiting sendMessageToUsers");
    }
  }

}
