package com.ohtacaesar.mail;

import com.ohtacaesar.mail.model.MailAddress;
import com.ohtacaesar.mail.model.MailAddressRepository;
import com.ohtacaesar.mail.model.MailMessage;
import com.ohtacaesar.mail.model.MailMessageRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class StorageMailSender implements MailSender {

  @Autowired
  private MailMessageRepository mailMessageRepository;

  @Autowired
  private MailAddressRepository mailAddressRepository;

  @Override
  public void send(SimpleMailMessage simpleMessage) throws MailException {
    send(new SimpleMailMessage[]{simpleMessage});
  }

  @Override
  public void send(SimpleMailMessage... simpleMessages) throws MailException {
    Date date = new Date();

    Map<String, MailAddress> map = mailAddressRepository.findAll().stream()
        .collect(Collectors.toMap(MailAddress::getAddress, o -> o));

    List<MailMessage> mailMessageList = Arrays.stream(simpleMessages)
        .map(MailMessage::createFrom)
        .peek(mm -> {
          mm.setSentDate(date);
          mm.setTo(mm.getTo().stream()
              .map(o -> map.getOrDefault(o.getAddress(), o))
              .collect(Collectors.toList()));
          mm.setCc(mm.getCc().stream()
              .map(o -> map.getOrDefault(o.getAddress(), o))
              .collect(Collectors.toList()));
          mm.setBcc(mm.getBcc().stream()
              .map(o -> map.getOrDefault(o.getAddress(), o))
              .collect(Collectors.toList()));
        })
        .collect(Collectors.toList());

    mailMessageRepository.save(mailMessageList);
  }
}
