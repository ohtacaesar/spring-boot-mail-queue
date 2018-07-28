package com.ohtacaesar.mail;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailMessageEntityRepository extends
    JpaRepository<MailMessageEntity, Integer> {

  List<MailMessageEntity> findOneByMailStatusOrderByIdAsc(MailStatus mailStatus);
}
