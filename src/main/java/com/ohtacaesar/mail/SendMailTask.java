package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Scheduled;

public class SendMailTask {

  @Autowired
  MailSender sender;

  @Scheduled(fixedDelay = 5000)
  public void reportCurrentTime() {
  }

}
