package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookRentRepositoryImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    private UUID bookRentId;
    private UUID userId;
    private BookRent testBookRent;


    private BookRentRepositoryImpl repository;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();

        userId = UUID.randomUUID();
        bookRentId = UUID.randomUUID();
        testBookRent = new BookRent();
        testBookRent.setUuid(bookRentId);
        testBookRent.setRentDate(java.time.LocalDateTime.now());
        testBookRent.setReturnDate(java.time.LocalDateTime.now().plusDays(7));

        User user = new User();
        user.setId(userId);
        testBookRent.setUser(user);

        em.getTransaction().begin();
        em.persist(user);
        em.persist(testBookRent);
        em.getTransaction().commit();

        repository = new BookRentRepositoryImpl(em);
    }

    @Test
    public void testFindById() {
        BookRent foundBookRent = repository.findById(bookRentId);

        assertNotNull(foundBookRent);
        assertEquals(bookRentId, foundBookRent.getUuid());
    }

    @Test
    public void testFindAll() {
        // Получаем все записи из базы
        List<BookRent> result = em.createQuery("SELECT r FROM BookRent r", BookRent.class).getResultList();

        // Проверяем, что результат не пустой
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSave() {
        // Сохраняем новый объект BookRent
        BookRent newBookRent = new BookRent();
        newBookRent.setUuid(UUID.randomUUID());
        newBookRent.setRentDate(java.time.LocalDateTime.now());
        newBookRent.setReturnDate(java.time.LocalDateTime.now().plusDays(7));

        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newBookRent.setUser(newUser);

        em.getTransaction().begin();
        em.persist(newUser);
        em.persist(newBookRent);
        em.getTransaction().commit();

        // Проверка, что объект был сохранен
        assertNotNull(newBookRent.getUuid());
    }

    @Test
    public void testFindByUserId() {
        // Выполняем поиск по userId
        List<BookRent> result = em.createQuery("SELECT r FROM BookRent r WHERE r.user.id = :userId", BookRent.class)
                .setParameter("userId", userId)
                .getResultList();

        // Проверяем, что результат не пустой
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindByIdNotFound() {
        // Попытка найти несуществующий объект
        UUID nonExistentId = UUID.randomUUID();
        BookRent result = em.find(BookRent.class, nonExistentId);
        assertNull(result);
    }

    @Test
    public void testFindByUserIdNotFound() {
        // Попытка найти BookRent по несуществующему userId
        UUID nonExistentUserId = UUID.randomUUID();
        List<BookRent> result = em.createQuery("SELECT r FROM BookRent r WHERE r.user.id = :userId", BookRent.class)
                .setParameter("userId", nonExistentUserId)
                .getResultList();
        assertTrue(result.isEmpty());
    }

    @AfterEach
    public void tearDown() {
        // Закрываем EntityManager после выполнения тестов
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
