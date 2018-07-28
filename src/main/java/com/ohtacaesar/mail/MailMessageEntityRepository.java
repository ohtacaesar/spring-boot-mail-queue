package com.ohtacaesar.mail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailMessageEntityRepository extends
    JpaRepository<MailMessageEntity, Integer> {

}
