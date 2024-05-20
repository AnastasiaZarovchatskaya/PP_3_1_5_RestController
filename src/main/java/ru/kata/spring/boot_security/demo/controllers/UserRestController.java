package ru.kata.spring.boot_security.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Показать пользователя
    @GetMapping()
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()).orElse(null));
    }


}

