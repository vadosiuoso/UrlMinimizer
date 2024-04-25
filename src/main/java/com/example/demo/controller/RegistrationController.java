package com.example.demo.controller;

import jakarta.validation.Valid;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new UserClass());
    return "register";
  }

  @PostMapping("/register")
  public ModelAndView registerUser(@ModelAttribute @Valid UserClass user, BindingResult result) {
    if (result.hasErrors()) {
      return new ModelAndView("register", "user", user);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return new ModelAndView("redirect:/login");
  }


}
