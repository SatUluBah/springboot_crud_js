package ru.ulush.springboot_crud.dao;

import org.springframework.stereotype.Repository;
import ru.ulush.springboot_crud.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> listRoles() {
        Query query = em.createQuery("SELECT role FROM Role role ");
        List<Role> roles = (List<Role>) query.getResultList();
        return roles;
    }
    @Override
    public Role getRole(long id) {
        return em.find(Role.class, id);

    }

    @Override
    public Role getRoleByName(String name) {
        Query query = em.createQuery("SELECT role FROM Role role WHERE role.name = :n");
        query.setParameter("n", name);
        Role role = (Role) query.getSingleResult();
        return role;
    }

}
