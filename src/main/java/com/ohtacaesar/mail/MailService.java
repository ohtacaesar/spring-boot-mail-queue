package com.ohtacaesar.mail;

import com.ohtacaesar.mail.model.MailMessage;
import com.ohtacaesar.mail.model.MailMessage.Status;
import com.ohtacaesar.mail.model.MailMessageRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

  @Autowired
  private MailMessageRepository repository;

  @Autowired
  private MailSender sender;

  public void send() {
    List<MailMessage> list = repository.findByMailStatusOrderByIdAsc(Status.NEW);
    if (list.size() == 0) {
      return;
    }
    MailMessage o = list.get(0);
    o.setMailStatus(Status.LOCK);
    o = repository.save(o);
    try {
      sender.send(o.createSimpleMailMessage());
      o.setMailStatus(Status.SENT);
      o.setSentDate(new Date());
    } catch (MailException e) {
      o.setMailStatus(Status.NEW);
    } finally {
      repository.save(o);
    }
  }
}
