package com.ohtacaesar.mail.controller;

import com.ohtacaesar.mail.StorageMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/smm")
public class SimpleMailMessageController {

  @Autowired
  private StorageMailSender mailSender;

  @GetMapping("/new")
  public String newMessage(SimpleMailMessage smm, Model model) {
    smm.setTo(new String[1]);
    model.addAttribute(smm);

    return "/smm/new";
  }

  @PostMapping("/new")
  public String createMessage(
      @Validated SimpleMailMessage smm,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return newMessage(smm, model);
    }

    mailSender.send(smm);

    return "redirect:/";
  }

}
