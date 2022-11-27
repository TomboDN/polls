package com.tombo.polls.controller;

import com.tombo.polls.model.User;
import com.tombo.polls.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/signup")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String register(User user) {
        userService.register(user);
        return "polls-list";
    }
}
