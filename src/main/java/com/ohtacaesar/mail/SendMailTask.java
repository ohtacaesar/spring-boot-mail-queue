package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SendMailTask {

  @Autowired
  private MailSender sender;

  @Autowired
  private MailMessageEntityRepository repository;

  @Scheduled(fixedDelay = 5000)
  public void reportCurrentTime() {
    MailMessageEntity msg = new MailMessageEntity();
    msg.setFrom("ohtacaesar@gmail.com");
    msg.setTo("ohtacaesar@gmail.com");
    msg.setSubject("test");
    msg.setText("test");
    System.out.println(msg);

    repository.save(msg);
  }

}
