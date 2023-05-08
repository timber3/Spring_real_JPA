package com.real_JPA.JPA_SHOP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class shopController {

    @GetMapping("Hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello!!");
        return "Hello";
    }
}
