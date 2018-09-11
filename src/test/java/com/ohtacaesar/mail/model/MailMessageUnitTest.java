package com.ohtacaesar.mail.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    original.setFrom("From");
    original.setReplyTo("ReplyTo");
    original.setTo("To");
    original.setCc("Cc");
    original.setBcc("Bcc");
    original.setSentDate(date);
    original.setSubject("Subject");
    original.setText("Text");
  }

  @Test
  public void createFrom() {
    MailMessage mailMessage = MailMessage.createFrom(original);
    assertEquals(original.getFrom(), mailMessage.getFrom());
    assertEquals(original.getReplyTo(), mailMessage.getReplyTo());
    assertArrayEquals(original.getTo(), mailMessage.getToArray());
    assertArrayEquals(original.getCc(), mailMessage.getCcArray());
    assertArrayEquals(original.getBcc(), mailMessage.getBccArray());
    assertEquals(original.getSentDate(), mailMessage.getSentDate());
    assertEquals(original.getText(), mailMessage.getText());
    assertEquals(original.getSubject(), mailMessage.getSubject());
  }

  @Test
  public void createSimpleMailMessage() {
    SimpleMailMessage smm = MailMessage.createFrom(original).createSimpleMailMessage();
    assertEquals(original.getFrom(), smm.getFrom());
    assertEquals(original.getReplyTo(), smm.getReplyTo());
    assertArrayEquals(original.getTo(), smm.getTo());
    assertArrayEquals(original.getCc(), smm.getCc());
    assertArrayEquals(original.getBcc(), smm.getBcc());
    assertEquals(original.getSentDate(), smm.getSentDate());
    assertEquals(original.getSubject(), smm.getSubject());
    assertEquals(original.getText(), smm.getText());
  }

  @Test
  public void copy() {
    MailMessage o1 = MailMessage.createFrom(original);
    MailMessage o2 = o1.copy();

    assertEquals(o1.getFrom(), o2.getFrom());
    assertEquals(o1.getReplyTo(), o2.getReplyTo());
    assertArrayEquals(o1.getToArray(), o2.getToArray());
    assertArrayEquals(o1.getCcArray(), o2.getCcArray());
    assertArrayEquals(o1.getBccArray(), o2.getBccArray());
    assertNull(o2.getSentDate());
    assertEquals(o1.getSubject(), o2.getSubject());
    assertEquals(o1.getText(), o2.getText());
  }
}
