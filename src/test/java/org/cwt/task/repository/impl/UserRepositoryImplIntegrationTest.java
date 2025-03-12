package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.model.entity.User;
import org.cwt.task.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private UserRepositoryImpl repository;

    private UUID userId;
    private User testUser;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();

        repository = new UserRepositoryImpl(em);

        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setFirstName("TestUser");
        testUser.setLastName("TestLastName");

        em.getTransaction().begin();
        em.persist(testUser);
        em.getTransaction().commit();

        userId = testUser.getId();
    }

    @Test
    public void testFindByUsername() {
        User foundUser = repository.findByUsername("TestUser");

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("TestUser", foundUser.getFirstName());
    }

    @Test
    public void testFindAll() {
        List<User> result = repository.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userId, result.get(0).getId());
    }

    @Test
    public void testSave() {
        User newUser = new User();
        UUID newUserId = UUID.randomUUID();
        newUser.setId(newUserId);
        newUser.setFirstName("NewUser");
        newUser.setLastName("NewLastName");

        repository.save(newUser);
        User savedUser = repository.findById(newUserId);

        assertNotNull(savedUser);
        assertEquals(newUserId, savedUser.getId());
        assertEquals("NewUser", savedUser.getFirstName());
        assertEquals("NewLastName", savedUser.getLastName());
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(userId);

        em.getTransaction().begin();
        User deletedUser = em.find(User.class, userId);
        em.getTransaction().commit();

        assertNull(deletedUser);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> repository.findById(nonExistentId));
    }

    @Test
    public void testFindByUsernameNotFound() {
        String nonExistentUsername = "NonExistentUser";
        assertThrows(NotFoundException.class, () -> repository.findByUsername(nonExistentUsername));
    }

    @AfterEach
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
