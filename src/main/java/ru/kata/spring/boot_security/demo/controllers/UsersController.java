package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UsersController {
    private UserServiceImp userService;
    private RoleServiceImp roleService;

    @Autowired
    public UsersController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // стартовая страница
    @GetMapping
    public String startPage() {
        return "startpage";
    }

    //создание нового пользователя
    @GetMapping(value = "/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "newUser";
    }

    // Сохранение нового пользователя
    @PostMapping()
    public String save(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    // Показываем данные авторизованного пользователя
    @GetMapping("/{id}")
    public String showUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        return "showUser";
    }

}
