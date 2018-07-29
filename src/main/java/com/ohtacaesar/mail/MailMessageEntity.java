package com.ohtacaesar.mail;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import lombok.Data;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;

@Entity
@Data
public class MailMessageEntity implements MailMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Version
  private int version;

  @Enumerated(EnumType.ORDINAL)
  private MailStatus mailStatus = MailStatus.NEW;

  @Column(nullable = false)
  private String from;

  private String replyTo;

  @Column(nullable = false)
  @Convert(converter = StringArrayConverter.class)
  private String[] to;

  @Convert(converter = StringArrayConverter.class)
  private String[] cc;

  @Convert(converter = StringArrayConverter.class)
  private String[] bcc;

  private Date sentDate;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false)
  @Lob
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

  public SimpleMailMessage createSimpleMailMessage() {
    SimpleMailMessage smm = new SimpleMailMessage();
    smm.setFrom(this.getFrom());
    smm.setReplyTo(this.getReplyTo());
    smm.setTo(this.getTo());
    smm.setCc(this.getCc());
    smm.setBcc(this.getBcc());
    smm.setSentDate(this.getSentDate());
    smm.setSubject(this.getSubject());
    smm.setText(this.getText());

    return smm;
  }

  public MailMessageEntity copy() {
    MailMessageEntity n = new MailMessageEntity();
    n.setFrom(this.getFrom());
    n.setReplyTo(this.getReplyTo());
    n.setTo(this.getTo());
    n.setCc(this.getCc());
    n.setBcc(this.getBcc());
    n.setSubject(this.getSubject());
    n.setText(this.getText());
    return n;
  }
}
