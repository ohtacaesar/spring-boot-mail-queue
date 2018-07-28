package com.ohtacaesar.mail;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

  @Autowired
  private MailMessageEntityRepository repository;

  @Autowired
  private MailSender sender;

  @Transactional
  public void send() {
    List<MailMessageEntity> list = repository.findOneByMailStatusOrderByIdAsc(MailStatus.NEW);
    if (list.size() == 0) {
      return;
    }

    MailMessageEntity e = list.get(0);
    System.out.println(e);
    sender.send(e.createSimpleMailMessage());
    e.setMailStatus(MailStatus.SENT);
    e.setSentDate(new Date());
    repository.save(e);
  }
}
