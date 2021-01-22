package ru.ulush.springboot_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulush.springboot_crud.dao.RoleDao;
import ru.ulush.springboot_crud.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{


    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

}
