package com.ohtacaesar.mail;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

  @Autowired
  private MailMessageEntityRepository repository;

  @Autowired
  private MailSender sender;

  public void send() {
    List<MailMessageEntity> list = repository.findByMailStatusOrderByIdAsc(MailStatus.NEW);
    if (list.size() == 0) {
      return;
    }
    MailMessageEntity o = list.get(0);
    o.setMailStatus(MailStatus.LOCK);
    repository.save(o);
    try {
      sender.send(o.createSimpleMailMessage());
      o.setMailStatus(MailStatus.SENT);
    } catch (MailException e) {
      o.setMailStatus(MailStatus.NEW);
    } finally {
      o.setVersion(o.getVersion() + 1);
      repository.save(o);
    }
  }
}
