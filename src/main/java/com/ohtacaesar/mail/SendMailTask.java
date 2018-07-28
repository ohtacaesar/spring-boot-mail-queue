package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SendMailTask {


  @Autowired
  private MailService service;

  @Autowired
  private MailMessageEntityRepository repository;


  @Scheduled(fixedDelay = 3000)
  public void sendMail() {
    service.send();
  }

  @Scheduled(fixedDelay = 1000)
  public void enqueueMail() {
    MailMessageEntity msg = new MailMessageEntity();
    msg.setFrom("ohtacaesar@gmail.com");
    msg.setTo("ohtacaesar@gmail.com");
    msg.setSubject("test");
    msg.setText("test");
    repository.save(msg);
  }

}
