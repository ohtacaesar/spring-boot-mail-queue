package com.ohtacaesar.mail.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailAddressRepository extends JpaRepository<MailAddress, Integer> {
}
