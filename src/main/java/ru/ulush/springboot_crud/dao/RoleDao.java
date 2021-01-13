package ru.ulush.springboot_crud.dao;


import ru.ulush.springboot_crud.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> listRoles();
    Role getRole(long id);
}
