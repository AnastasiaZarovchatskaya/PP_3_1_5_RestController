package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;



import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //показать всех пользователей из БД
    @GetMapping()
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("thisUser", userService.findByUsername(principal.getName()).get());
        return "admin";
    }

    //обновление пользователя
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user")  User user, @PathVariable("id") Long id) {
        userService.edit(id, user);
        return "redirect:/admin";
    }

    //удаление пользователя
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    // Сохранение нового пользователя
    @PostMapping()
    public String save(@ModelAttribute("user")  User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

}
