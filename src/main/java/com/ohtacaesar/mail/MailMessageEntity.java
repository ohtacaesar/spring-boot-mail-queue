package com.ohtacaesar.mail;

import java.util.Date;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailParseException;

@Entity
@Data
public class MailMessageEntity implements MailMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String from;

  private String replyTo;

  @Convert(converter = StringArrayConverter.class)
  private String[] to;

  @Convert(converter = StringArrayConverter.class)
  private String[] cc;

  @Convert(converter = StringArrayConverter.class)
  private String[] bcc;

  private Date sentDate;

  private String subject;

  private String text;


  @Override
  public void setTo(String to) throws MailParseException {
    this.to = new String[]{to};
  }

  @Override
  public void setTo(String[] to) throws MailParseException {
    this.to = to;
  }

  @Override
  public void setCc(String cc) throws MailParseException {
    this.cc = new String[]{cc};

  }

  @Override
  public void setCc(String[] cc) throws MailParseException {
    this.cc = cc;
  }

  @Override
  public void setBcc(String bcc) throws MailParseException {
    this.bcc = new String[]{bcc};
  }

  @Override
  public void setBcc(String[] bcc) throws MailParseException {
    this.bcc = bcc;
  }
}
