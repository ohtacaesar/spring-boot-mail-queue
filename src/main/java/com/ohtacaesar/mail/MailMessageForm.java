package com.ohtacaesar.mail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
class MailMessageForm {

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Email
  private String from;

  @Size(max = 255)
  @Email
  private String replyTo;

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Email
  private String to;

  @NotNull
  @NotBlank
  @Size(max = 255)
  private String subject;

  @NotNull
  @NotBlank
  private String text;

}
