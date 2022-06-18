package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.*;
import ru.kata.spring.boot_security.demo.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleService roleService;

    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void updateUser(User user, String role) {
        user.setRoles(roleExistenceCheck(role));
        user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
        userDao.setUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    @Transactional
    public void setUser(User user, String role) {
        user.setRoles(roleExistenceCheck(role));
        user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
        userDao.setUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails getSpecificUsername(String username) throws UsernameNotFoundException {
        return userDao.getSpecificUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getSpecificUser(String username) throws UsernameNotFoundException {
        return userDao.getSpecificUsername(username);
    }

    @Transactional
    public void testSetUser(User user) {
        user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
        userDao.setUser(user);
    }

    public Set<Role> roleExistenceCheck(String role){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole("USER"));
        if (role != null && role.equals("ADMIN")) {
            roles.add(roleService.getRole("ADMIN"));
        }
        return roles;
    }
}
