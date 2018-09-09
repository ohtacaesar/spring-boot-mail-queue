package com.ohtacaesar.mail.model;

import com.ohtacaesar.mail.model.MailMessage.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailMessageRepository extends
    JpaRepository<MailMessage, Integer> {

  List<MailMessage> findByMailStatusOrderByIdAsc(Status mailStatus);
}
