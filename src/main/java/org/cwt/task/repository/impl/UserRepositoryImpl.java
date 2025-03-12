package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.cwt.task.model.entity.User;
import org.cwt.task.exception.NotFoundException;
import org.cwt.task.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private EntityManager em;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        try {
            return em.createQuery("select u from User u " +
                            "where u.firstName = :username", User.class).setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException ex){
            throw new NotFoundException("Not found user with username: " + username);
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }

    @Override
    public User findById(UUID id) {
        try {
            return em.createQuery("select u from User u " +
                            "where u.id = :id", User.class).setParameter("id", id)
                    .getSingleResult();
        }
        catch (NoResultException ex){
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

    @Override
    public void deleteById(UUID id) {
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }


}
