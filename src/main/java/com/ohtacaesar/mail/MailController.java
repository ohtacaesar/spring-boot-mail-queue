package com.ohtacaesar.mail;

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
  private MailMessageEntityRepository repository;

  @GetMapping
  public String index(
      @PageableDefault(size = 20, direction = Direction.DESC, sort = {"id"}) Pageable pageable,
      MailMessageEntity mailMessageEntity,
      Model model
  ) {
    Page<MailMessageEntity> page = repository.findAll(pageable);
    model.addAttribute(mailMessageEntity);
    model.addAttribute("page", page);

    return "index";
  }

  @GetMapping("{id}/text")
  @ResponseBody
  public String text(@PathVariable int id) {
    MailMessageEntity e = repository.findOne(id);
    return e.getText();
  }

  @PostMapping("send")
  public String send(
      @PageableDefault(size = 20, direction = Direction.DESC, sort = {"id"}) Pageable pageable,
      @Validated MailMessageEntity mailMessageEntity,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return index(pageable, mailMessageEntity, model);
    }
    repository.save(mailMessageEntity);

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

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

}
