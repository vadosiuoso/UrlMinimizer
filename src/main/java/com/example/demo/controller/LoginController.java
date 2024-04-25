package com.example.demo.controller;

import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  @Autowired
  private UserRepository usersRepository;

  @GetMapping
  public String index(Model model) {
    Iterable<UserClass> users = usersRepository.findAll();
    model.addAttribute("users", users);
    return "index";
  }

  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error,
                      @RequestParam(value = "logout", required = false) String logout,
                      Model model) {
    if (error != null) {
      model.addAttribute("message", "Ваше ім'я користувача та пароль недійсні.");
    }
    if (logout != null) {
      model.addAttribute("message", "Ви успішно вийшли з системи.");
    }
    return "login";
  }

  @GetMapping("/logout-success")
  public String logout(Model model) {
    model.addAttribute("message", "Ви успішно вийшли з системи.");
    return "login";
  }

  @GetMapping("/reg")
  public String reg(Model model) {
    return "reg";
  }

  @GetMapping("/aсcount")
  public String aсcount(Model model) {
    return "acсount";
  }
}
