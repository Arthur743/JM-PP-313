package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class NewUsersOfDB {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public NewUsersOfDB(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsersWithRoles() {

        try {
            Role checkRole = roleService.getRole("ADMIN");
        } catch (Exception e) {
            roleService.setRole(new Role("ADMIN"));
        }
        try {
            Role checkRole = roleService.getRole("USER");
        } catch (Exception e) {
            roleService.setRole(new Role("USER"));
        }
        try {
            User user = userService.getSpecificUser("Faina");
        } catch (Exception e) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRole("ADMIN"));
            roles.add(roleService.getRole("USER"));
            userService.setUser(new User("Faina", "Amandosova", "1","11", roles));
        }
        try {
            User user = userService.getSpecificUser("Diana");
        } catch (Exception e) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRole("USER"));
            userService.setUser(new User("Diana", "Aktuganova", "2", "22", roles));
        }

    }
}
