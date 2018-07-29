package com.ohtacaesar.mail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
class MailMessageForm {

  @NotNull
  @Size(max = 255)
  @Email
  private String from;

  @Size(max = 255)
  private String replyTo;

  @NotNull
  @Size(max = 255)
  @Email
  private String to;

  @NotNull
  @Size(max = 255)
  private String subject;

  @NotNull
  private String text;

}
