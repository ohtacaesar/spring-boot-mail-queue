package com.ohtacaesar.mail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Data
@ToString(of = {"id", "address"})
public class MailAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Email
  @NotBlank
  @NotNull
  @Column(length = 100, nullable = false, unique = true)
  private String address;

  public static MailAddress createWithAddress(String address) {
    MailAddress o = new MailAddress();
    o.setAddress(address);

    return o;
  }

}
