package ru.ulush.springboot_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ulush.springboot_crud.model.Role;
import ru.ulush.springboot_crud.model.User;
import ru.ulush.springboot_crud.service.RoleService;
import ru.ulush.springboot_crud.service.UserService;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/admin")
    public String getUsers(Model model, Principal principal) {
        Long principalId = userService.getUserByName(principal.getName()).getId();
        User newUser = new User();
        List<Role> showRoles = roleService.listRoles();
        model.addAttribute("newUser", newUser);
        model.addAttribute("showRoles", showRoles);
        model.addAttribute("userId", userService.getUser(principalId));
        model.addAttribute("users", userService.listUsers());
        return "admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("editUser", userService.getUser(id));
        model.addAttribute("showRoles", roleService.listRoles());
        return "redirect:/admin";
    }

    @GetMapping("/userspace/{id}")
    public String userspace(Model model, @PathVariable("id") long id, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        if(!user.getId().equals(id)) {
            return "redirect:/access-denied";
        }
        model.addAttribute("user", userService.getUser(id));
        return "userspace";
    }

    @PostMapping("/admin/new")
    public String createUser(Model model, @ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(Model model, @ModelAttribute("user") User user) {
        if(!user.getPassword().equals(userService.getUser(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") long id) {
        userService.deleteUser(userService.getUser(id));
        model.addAttribute("users", userService.listUsers());
        return "redirect:/admin";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
