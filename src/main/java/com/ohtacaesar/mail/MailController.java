package com.ohtacaesar.mail;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
      MailMessageForm form,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
  ) {
    Page<MailMessageEntity> page = repository.findAll(pageable);

    mav.addObject("page", page);
    mav.addObject("mails", page.getContent());
    mav.addObject("form", form);
    mav.setViewName("index");

    return mav;
  }

  @GetMapping("{id}/text")
  @ResponseBody
  public String text(@PathVariable int id) {
    MailMessageEntity e = repository.findOne(id);
    return e.getText();
  }

  @PostMapping("{id}/resend")
  public String resend(@PathVariable int id) {
    MailMessageEntity e = repository.findOne(id);
    e = e.copy();
    e.setSubject("Copy from" + id);
    repository.save(e);

    return "redirect:/";
  }

  @PostMapping("send")
  public String create(@Validated @ModelAttribute MailMessageForm form) {
    MailMessageEntity o = new MailMessageEntity();
    BeanUtils.copyProperties(form, o);
    o.setTo(form.getTo());
    repository.save(o);

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
