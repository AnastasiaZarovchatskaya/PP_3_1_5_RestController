package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;

    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    // получить список пользователей из БД
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //показать пользователя по ID

    @Override
    public User showUser(Long id) {
        return userRepository.findById(id).get();
    }

    //сохранить пользователя
    @Transactional
    @Override
    public void saveUser(User user) {
        logger.info("Saving user: {}", user.getUsername());

        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.findRoleByRole(role.getRole()))
                .collect(Collectors.toSet()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        logger.info("User saved successfully: {}", user.getUsername());
    }

    //удалить пользователя по ID
    @Transactional
    @Override
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    // Редактирование пользователя
    @Transactional
    @Override
    public void edit(User user) {
        User existingUser = showUser(user.getId()); // получаем существующего пользователя из репозитория
        if (user.getPassword().isEmpty()) { // если пароль пустой
            user.setPassword(existingUser.getPassword());// то пароль будет равным паролю существующего пользователя.
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // иначе закодировать пароль нового пользователя с использованием passwordEncoder
        }
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.findRoleByRole(role.getRole()))
                .collect(Collectors.toSet()));

        userRepository.save(user); // сохраняем нового пользователя в репозиторий
    }

    //получение пользователя по имени
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        if (user.isEmpty()) { // если пользователя нет, то выбрасывается исколючение
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user.get();
    }
}
