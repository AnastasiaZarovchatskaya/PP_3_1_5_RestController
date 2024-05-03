package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//    @NotEmpty(message = "Электронная почта не должна быть пустой")
//    @Email(message = "Электронная почта должна быть действующей")
    @Column(name = "email")
    private String username;
//    @NotEmpty(message = "Имя не должно быть пустым")
//    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    @Column(name = "first_name")
    private String firstName;
//    @NotEmpty(message = "Фамилия не должна быть пустой")
//    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов")
    @Column(name = "last_name")
    private String lastName;

//    @Min(value = 0, message = "Возраст должен быть больше 0")
    @Column(name = "age")
    private int age;

//    @NotEmpty(message = "Фамилия не должна быть пустой")
//    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов")
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Role> roles;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}