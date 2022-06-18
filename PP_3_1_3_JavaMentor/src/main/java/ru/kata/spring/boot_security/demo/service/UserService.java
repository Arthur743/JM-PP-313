package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.*;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    void updateUser(User user, String role);

    User getUser(int id);

    void deleteUser(User user);

    void setUser(User user, String role);

    UserDetails getSpecificUsername(String s);

    User getSpecificUser(String username);

    void testSetUser(User user);
}
