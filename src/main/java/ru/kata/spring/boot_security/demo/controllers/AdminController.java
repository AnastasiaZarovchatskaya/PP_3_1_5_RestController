package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;


@Controller
@RequestMapping("/users/admin")
public class AdminController {
    private UserServiceImp userService;
    private RoleServiceImp roleService;
@Autowired
    public AdminController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //показать всех пользователей из БД
    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("admin", userService.getAllUsers());
        model.addAttribute("roles", roleService.findAll());
        return "adminPage";
    }
    //редактирование пользователя
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit";
    }

    //обновление пользователя
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) { //есть ли ошибки в данном объекте
            return "edit"; //возвращаем ту форму с ошибками
        }

        userService.edit(id,user);
        return "redirect:/users/admin";
    }
//@PatchMapping(value = "/{id}")
//public String update(@PathVariable("id") long id, @ModelAttribute("user") User user) {
//    userService.edit(id, user);
//    return "redirect:/users/admin";
//}
    //удаление пользователя
    @DeleteMapping ("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
            return "redirect:/users/admin";
    }

    //показывыть данные пользователя
    @GetMapping(value = "/{id}/showUser")
    public String showUser(@PathVariable("id") Long id, Model model){
    model.addAttribute("user", userService.showUser(id));
    return "showUser";
    }

}
