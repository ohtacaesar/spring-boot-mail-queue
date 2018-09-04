package com.ohtacaesar.mail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
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

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Email
  @Column(nullable = false)
  private String from;

  @Size(max = 255)
  @Email
  private String replyTo;

  @NotNull
  @Size(min = 1, max = 10)
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> to = Arrays.asList("", "", "");

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> cc = Arrays.asList("");

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> bcc = Arrays.asList("");

  private Date sentDate;

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Column(nullable = false)
  private String subject;

  @NotNull
  @NotBlank
  @Lob
  @Column(nullable = false)
  private String text;

  public String[] getToArray() {
    return to.toArray(new String[0]);
  }

  @Override
  public void setTo(String to) throws MailParseException {
    this.to = Arrays.asList(to);
  }

  @Override
  public void setTo(String[] to) throws MailParseException {
    this.to = Arrays.asList(to);
  }

  public String[] getCcArray() {
    return cc.toArray(new String[0]);
  }

  @Override
  public void setCc(String cc) throws MailParseException {
    this.cc = Arrays.asList(cc);
  }

  @Override
  public void setCc(String[] cc) throws MailParseException {
    this.cc = Arrays.asList(cc);
  }

  public String[] getBccArray() {
    return bcc.toArray(new String[0]);
  }

  @Override
  public void setBcc(String bcc) throws MailParseException {
    this.bcc = Arrays.asList(bcc);
  }

  @Override
  public void setBcc(String[] bcc) throws MailParseException {
    this.bcc = Arrays.asList(bcc);
  }

  public SimpleMailMessage createSimpleMailMessage() {
    SimpleMailMessage smm = new SimpleMailMessage();
    smm.setFrom(this.getFrom());
    smm.setReplyTo(this.getReplyTo());
    smm.setTo(this.getToArray());
    smm.setCc(this.getCcArray());
    smm.setBcc(this.getBccArray());
    smm.setSentDate(this.getSentDate());
    smm.setSubject(this.getSubject());
    smm.setText(this.getText());

    return smm;
  }

  public MailMessageEntity copy() {
    MailMessageEntity n = new MailMessageEntity();
    n.setFrom(this.getFrom());
    n.setReplyTo(this.getReplyTo());
    n.setTo(this.getToArray());
    n.setCc(this.getCcArray());
    n.setBcc(this.getBccArray());
    n.setSubject(this.getSubject());
    n.setText(this.getText());
    return n;
  }
}
