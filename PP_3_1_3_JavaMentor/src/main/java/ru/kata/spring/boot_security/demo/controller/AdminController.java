package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.*;
import ru.kata.spring.boot_security.demo.service.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String printAllUsers(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("activeUser", userService.getSpecificUser(principal.getName()));
        return "users";
    }

    @PostMapping("/users")
    public String createNewUser(@ModelAttribute("newUser") User newUser, @RequestParam(required = false) String roleAdd) {
        userService.setUser(userService.roleExistenceCheck(newUser, roleAdd));
        return "redirect:/admin/users";
    }

    @PatchMapping("/update")
    public String updateOldUser(@ModelAttribute(name = "user") User user, @RequestParam(required = false) String role) {
        userService.updateUser(userService.roleExistenceCheck(user, role));
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete")
    public String deleteOldUser(@ModelAttribute(name = "user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(int id) {
        return userService.getUser(id);
    }

    @GetMapping("/myInfo")
    public String showUserInfo(Principal principal, Model model) {
        model.addAttribute("activeUser", userService.getSpecificUser(principal.getName()));
        return "user";
    }
}