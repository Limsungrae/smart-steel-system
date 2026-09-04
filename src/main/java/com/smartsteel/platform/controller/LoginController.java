package com.smartsteel.platform.controller;

import com.smartsteel.platform.entity.User;
import com.smartsteel.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup/process")
    public String signupProcess(User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }
}
