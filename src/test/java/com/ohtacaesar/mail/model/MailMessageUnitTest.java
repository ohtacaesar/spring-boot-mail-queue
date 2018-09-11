package com.ohtacaesar.mail.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mail.SimpleMailMessage;

@RunWith(JUnit4.class)
public class MailMessageUnitTest {

  private SimpleMailMessage original;

  @Before
  public void setUp() {
    Date date = new Date();
    original = new SimpleMailMessage();
    original.setText("Text");
    original.setSubject("Subject");
    original.setSentDate(date);
    original.setBcc("Bcc");
    original.setCc("Cc");
    original.setTo("To");
    original.setReplyTo("ReplyTo");
    original.setFrom("From");

  }

  @Test
  public void createFrom() {
    MailMessage mailMessage = MailMessage.createFrom(original);
    assertEquals(1, mailMessage.getTo().size());
    assertEquals(original.getTo()[0], mailMessage.getTo().get(0).getAddress());
    assertEquals(1, mailMessage.getCc().size());
    assertEquals(original.getCc()[0], mailMessage.getCc().get(0).getAddress());
    assertEquals(1, mailMessage.getTo().size());
    assertEquals(original.getBcc()[0], mailMessage.getBcc().get(0).getAddress());
    assertEquals(original.getText(), mailMessage.getText());
    assertEquals(original.getSubject(), mailMessage.getSubject());
    assertEquals(original.getSentDate(), mailMessage.getSentDate());
    assertEquals(original.getReplyTo(), mailMessage.getReplyTo());
    assertEquals(original.getFrom(), mailMessage.getFrom());
  }

  @Test
  public void createSimpleMailMessage() {
    SimpleMailMessage sms = MailMessage.createFrom(original).createSimpleMailMessage();
    assertEquals(1, sms.getTo().length);
    assertEquals(original.getTo()[0], sms.getTo()[0]);
    assertEquals(1, sms.getCc().length);
    assertEquals(original.getCc()[0], sms.getCc()[0]);
    assertEquals(1, sms.getTo().length);
    assertEquals(original.getBcc()[0], sms.getBcc()[0]);
    assertEquals(original.getText(), sms.getText());
    assertEquals(original.getSubject(), sms.getSubject());
    assertEquals(original.getSentDate(), sms.getSentDate());
    assertEquals(original.getReplyTo(), sms.getReplyTo());
    assertEquals(original.getFrom(), sms.getFrom());
  }
}
