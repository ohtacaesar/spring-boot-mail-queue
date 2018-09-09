package com.ohtacaesar.mail;

import com.ohtacaesar.mail.model.MailAddress;
import com.ohtacaesar.mail.model.MailAddressRepository;
import com.ohtacaesar.mail.model.MailMessage;
import com.ohtacaesar.mail.model.MailMessageRepository;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

  @Autowired
  private MailMessageRepository messageRepository;

  @Autowired
  private MailAddressRepository addressRepository;

  @GetMapping
  public String index(
      @PageableDefault(size = 20, direction = Direction.DESC, sort = {"id"}) Pageable pageable,
      MailMessage mailMessage,
      Model model
  ) {
    Page<MailMessage> page = messageRepository.findAll(pageable);
    model.addAttribute(mailMessage);
    model.addAttribute("page", page);

    return "index";
  }

  @GetMapping("{id}/text")
  @ResponseBody
  public String text(@PathVariable int id) {
    MailMessage e = messageRepository.findOne(id);
    return e.getText();
  }

  @PostMapping("send")
  public String send(
      @PageableDefault(size = 20, direction = Direction.DESC, sort = {"id"}) Pageable pageable,
      @Validated MailMessage mailMessage,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return index(pageable, mailMessage, model);
    }

    Map<String, MailAddress> map = addressRepository.findAll().stream()
        .collect(Collectors.toMap(MailAddress::getAddress, o -> o));

    mailMessage.setTo(mailMessage.getTo().stream()
        .map(o -> map.getOrDefault(o.getAddress(), o))
        .collect(Collectors.toList()));

    messageRepository.save(mailMessage);

    return "redirect:/";
  }

  @PostMapping("{id}/resend")
  public String resend(@PathVariable int id) {
    MailMessage e = messageRepository.findOne(id);
    e = e.copy();
    e.setSubject("Copy from" + id);
    messageRepository.save(e);

    return "redirect:/";
  }

  @Autowired
  private MailService service;

  @PostMapping("task")
  public String task() {
    try {
      service.send();
    } catch (Exception ignore) {

    }

    return "redirect:/";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

}
