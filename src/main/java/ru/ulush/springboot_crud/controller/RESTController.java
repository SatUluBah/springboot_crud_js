package ru.ulush.springboot_crud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ulush.springboot_crud.model.Role;
import ru.ulush.springboot_crud.model.User;
import ru.ulush.springboot_crud.service.RoleService;
import ru.ulush.springboot_crud.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RESTController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.listRoles();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public List<User> newUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoles(user);
        userService.add(user);
        return userService.listUsers();
    }

    @PutMapping("/users")
    public List<User> updateUser(@RequestBody User user){
        if(!user.getPassword().equals(userService.getUser(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        setRoles(user);
        userService.updateUser(user);
        return userService.listUsers();
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUser(@PathVariable long id){
        userService.deleteUser(userService.getUser(id));
        return userService.listUsers();
    }

    private void setRoles(User user) {
        Set<Role> receivedRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();
        for (Role r : receivedRoles) {
            roles.add(roleService.getRoleByName(r.getName()));
        }
        user.setRoles(roles);
    }
}
