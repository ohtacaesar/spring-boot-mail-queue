package com.ohtacaesar.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MailController {

  @Autowired
  private MailMessageEntityRepository repository;

  @GetMapping
  public ModelAndView index(
      ModelAndView mav,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
  ) {
    Page<MailMessageEntity> page = repository.findAll(pageable);

    mav.addObject("page", page);
    mav.addObject("mails", page.getContent());
    mav.setViewName("index");

    return mav;
  }

  @GetMapping("{id}/text")
  @ResponseBody
  public String text(@PathVariable int id) {
    MailMessageEntity e = repository.findOne(id);
    return e.getText();
  }


  @PostMapping("send")
  public String send() {
    MailMessageEntity e = new MailMessageEntity();
    e.setFrom("ohtacaesar@gmail.com");
    e.setTo("ohtacaesar@gmail.com");
    e.setSubject("テストメール");
    e.setText("テキストメール\r\nテスト");
    repository.save(e);

    return "redirect:/";
  }

  @PostMapping("{id}/resend")
  public String resend(@PathVariable int id) {
    MailMessageEntity e = repository.findOne(id);
    e = e.copy();
    e.setSubject("Copy from" + id);
    repository.save(e);

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
}
