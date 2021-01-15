package ru.ulush.springboot_crud.dao;



import ru.ulush.springboot_crud.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> listUsers();
    User getUser(long id);
    void deleteUser(User user);
    void updateUser(User user);
    User getUserByName(String name);
    User getUserByEmail(String email);
}
