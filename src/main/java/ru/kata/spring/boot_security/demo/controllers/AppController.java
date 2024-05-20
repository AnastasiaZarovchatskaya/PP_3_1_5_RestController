package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class AppController {
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}
