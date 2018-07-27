package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
    try (ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args)) {
      ctx.getBean(Application.class).sendMail();
    }
  }

  @Autowired
  private MailSender sender;

  public void sendMail() {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("ohtacaesar@gmail.com");
    msg.setTo("ohtacaesar@gmail.com");
    msg.setSubject("test");
    msg.setText("test");

    sender.send(msg);
  }

}
