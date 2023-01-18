package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired()
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roleList", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("userRole", userService.findByUsername(user.getUsername()));
        return "adminPanel";
    }

    @GetMapping("/admin-info")
    public String getAdminProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "adminInfo";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
