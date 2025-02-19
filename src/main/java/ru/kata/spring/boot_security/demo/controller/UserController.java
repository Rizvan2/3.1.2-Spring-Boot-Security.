package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.security.CustomUser;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String userPage(Model model, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        model.addAttribute("user", customUser.getUser());
        return "user/user";
    }
}