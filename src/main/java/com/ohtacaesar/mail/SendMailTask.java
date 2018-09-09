package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SendMailTask {

  @Autowired
  private MailService service;

  //@Scheduled(fixedDelay = 3000)
  public void sendMail() {
    service.send();
  }

}
