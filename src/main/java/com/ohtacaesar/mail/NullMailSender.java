package com.ohtacaesar.mail;

import java.util.Random;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
@Profile("development")
public class NullMailSender implements MailSender {

  private Random random = new Random();

  @Override
  public void send(SimpleMailMessage simpleMessage) throws MailException {
    if (random.nextBoolean()) {
      throw new RandomMailException();
    }
  }

  @Override
  public void send(SimpleMailMessage... simpleMessages) throws MailException {
    if (random.nextBoolean()) {
      throw new RandomMailException();
    }
  }

  public static class RandomMailException extends MailException {

    public RandomMailException() {
      super("Random Exception");
    }
  }
}
