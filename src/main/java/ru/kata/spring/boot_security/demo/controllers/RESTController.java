package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Exception.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class RESTController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(RESTController.class);

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Principal principal){
        return ResponseEntity.ok(userService.findByUsername(principal.getName()).orElse(null));
    }
    @GetMapping("/admin")
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/admin")
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.saveUser(user);
        logger.info("User saved successfully: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.showUser(id);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/admin/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user == null) {
            throw new NoSuchUserException("Пользователь не найден");
        }
        userService.edit(user);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        User user = userService.showUser(id);
        if (user == null) {
            throw new NoSuchUserException("Пользователь с ID " + id + " не найден");
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("Пользователь с ID: "+ id + " был удален.");
    }

}
