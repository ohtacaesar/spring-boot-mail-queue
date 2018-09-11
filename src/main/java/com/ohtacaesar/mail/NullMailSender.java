package com.ohtacaesar.mail;

import java.util.Random;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

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
