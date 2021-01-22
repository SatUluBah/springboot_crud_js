package ru.ulush.springboot_crud.dao;


import org.springframework.stereotype.Repository;
import ru.ulush.springboot_crud.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager em;

    @Override
    public void add(User user) {
        em.persist(user);
    }

    @Override
    public List<User> listUsers() {

        Query query = em.createQuery("SELECT user FROM User user ");
        List<User> users = (List<User>) query.getResultList();
        return users;
    }

    @Override
    public User getUser(long id) {
        return em.find(User.class, id);

    }

    @Override
    public void deleteUser(User user) {

        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Override
    public void updateUser(User user) {
        User u = em.find(User.class, user.getId());
        em.detach(u);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setRoles(user.getRoles());
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setAge(user.getAge());
        u.setEmail(user.getEmail());
        em.merge(u);
    }

    @Override
    public User getUserByName(String name) {

        Query query = em.createQuery("SELECT user FROM User user where user.username = :n")
                .setParameter("n", name);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Query query = em.createQuery("SELECT user FROM User user where user.email = :n")
                .setParameter("n", email);
        User user = (User) query.getSingleResult();
        return user;
    }


}

