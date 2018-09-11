package com.ohtacaesar.mail.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;

@Entity
@Data
@ToString(of = {"id", "version"})
public class MailMessage implements org.springframework.mail.MailMessage {

  public enum Status {
    NEW,
    LOCK,
    SENT,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Version
  private int version;

  @Enumerated(EnumType.ORDINAL)
  private Status mailStatus = Status.NEW;

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Email
  @Column(nullable = false)
  private String from;

  @Size(max = 255)
  @Email
  private String replyTo;

  @Valid
  @NotNull
  @Size(min = 1, max = 10)
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "mail_message_to",
      joinColumns = @JoinColumn(name = "mail_message_id"),
      inverseJoinColumns = @JoinColumn(name = "mail_address_id")
  )
  private List<MailAddress> to = new ArrayList<>();

  @Valid
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "mail_message_cc",
      joinColumns = @JoinColumn(name = "mail_message_id"),
      inverseJoinColumns = @JoinColumn(name = "mail_address_id")
  )
  private List<MailAddress> cc = new ArrayList<>();

  @Valid
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "mail_message_bcc",
      joinColumns = @JoinColumn(name = "mail_message_id"),
      inverseJoinColumns = @JoinColumn(name = "mail_address_id")
  )
  private List<MailAddress> bcc = new ArrayList<>();

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
    return to.stream().map(MailAddress::getAddress).toArray(String[]::new);
  }

  public void setTo(List<MailAddress> to) throws MailParseException {
    this.to = to;
  }

  @Override
  public void setTo(String to) throws MailParseException {
    this.to = Arrays.asList(MailAddress.createWithAddress(to));
  }

  @Override
  public void setTo(String[] to) throws MailParseException {
    if (to != null) {
      this.to = Arrays.stream(to)
          .map(MailAddress::createWithAddress)
          .collect(Collectors.toList());
    }
  }

  public void setCc(List<MailAddress> cc) {
    this.cc = cc;
  }

  public String[] getCcArray() {
    return cc.stream().map(MailAddress::getAddress).toArray(String[]::new);
  }

  @Override
  public void setCc(String cc) throws MailParseException {
    this.cc = Arrays.asList(MailAddress.createWithAddress(cc));
  }

  @Override
  public void setCc(String[] cc) throws MailParseException {
    if (cc != null) {
      this.cc = Arrays.stream(cc)
          .map(MailAddress::createWithAddress)
          .collect(Collectors.toList());
    }
  }

  public String[] getBccArray() {
    return bcc.stream().map(MailAddress::getAddress).toArray(String[]::new);
  }

  public void setBcc(List<MailAddress> bcc) {
    this.bcc = bcc;
  }

  @Override
  public void setBcc(String bcc) throws MailParseException {
    this.bcc = Arrays.asList(MailAddress.createWithAddress(bcc));
  }

  @Override
  public void setBcc(String[] bcc) throws MailParseException {
    if (bcc != null) {
      this.bcc = Arrays.stream(bcc)
          .map(MailAddress::createWithAddress)
          .collect(Collectors.toList());
    }
  }

  public static MailMessage createFrom(SimpleMailMessage smm) {
    MailMessage mailMessage = new MailMessage();
    mailMessage.setFrom(smm.getFrom());
    mailMessage.setReplyTo(smm.getReplyTo());
    mailMessage.setTo(smm.getTo());
    mailMessage.setCc(smm.getCc());
    mailMessage.setBcc(smm.getBcc());
    mailMessage.setSentDate(smm.getSentDate());
    mailMessage.setSubject(smm.getSubject());
    mailMessage.setText(smm.getText());

    return mailMessage;
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

  public MailMessage copy() {
    MailMessage o = new MailMessage();
    o.setFrom(this.getFrom());
    o.setReplyTo(this.getReplyTo());
    o.setTo(new ArrayList<>(this.getTo()));
    o.setCc(new ArrayList<>(this.getCc()));
    o.setBcc(new ArrayList<>(this.getBcc()));
    o.setSubject(this.getSubject());
    o.setText(this.getText());

    return o;
  }
}
