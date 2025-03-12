package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.cwt.task.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnalyticRepositoryImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private AnalyticRepositoryImpl repository;
    private UserRepository userRepository;

    private UUID userId;
    private User testUser;
    private BookRent testBookRent;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();

        userRepository = mock(UserRepository.class);
        repository = new AnalyticRepositoryImpl(em, userRepository);

        // Создание тестового пользователя
        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");

        // Создание тестового BookRent
        testBookRent = new BookRent();
        testBookRent.setUser(testUser);
        testBookRent.setRentStatus(BookRent.RentStatus.OPENED);
        testBookRent.setRentDate(LocalDateTime.now().minusDays(1));

        // Сохранение данных в базу
        em.getTransaction().begin();
        em.persist(testUser);
        em.persist(testBookRent);
        em.getTransaction().commit();
    }

    @Test
    public void testGetUserAnalytic_WithStartTimeAndEndTime() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        when(userRepository.findById(userId)).thenReturn(testUser);

        UserAnalytic result = repository.getUserAnalytic(userId, startTime, endTime);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(1, result.getCountRent());
        assertEquals(1, result.getCountOpenRent());
        assertEquals(0, result.getCountCloseRent());
        assertTrue(result.getOpenBook().contains("Test Book"));
        assertTrue(result.getCloseBook().isEmpty());
    }

    @Test
    public void testGetBooksByStatus_OpenBooks() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        when(userRepository.findById(userId)).thenReturn(testUser);

        List<String> openBooks = repository.getBooksByStatus(userId, BookRent.RentStatus.OPENED, startTime, endTime);

        assertNotNull(openBooks);
        assertEquals(1, openBooks.size());
        assertTrue(openBooks.contains("Test Book"));
    }

    @Test
    public void testGetBooksByStatus_ClosedBooks() {
        // Изменяем статус на закрытый
        testBookRent.setRentStatus(BookRent.RentStatus.CLOSED);
        testBookRent.setReturnDate(LocalDateTime.now());

        em.getTransaction().begin();
        em.merge(testBookRent);  // обновляем запись
        em.getTransaction().commit();

        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        when(userRepository.findById(userId)).thenReturn(testUser);

        List<String> closedBooks = repository.getBooksByStatus(userId, BookRent.RentStatus.CLOSED, startTime, endTime);

        assertNotNull(closedBooks);
        assertEquals(1, closedBooks.size());
        assertTrue(closedBooks.contains("Test Book"));
    }

    @Test
    public void testGetUserAnalytic_NoRentData() {
        UUID newUserId = UUID.randomUUID();
        User newUser = new User();
        newUser.setId(newUserId);
        newUser.setFirstName("Jane");
        newUser.setLastName("Smith");

        when(userRepository.findById(newUserId)).thenReturn(newUser);

        UserAnalytic result = repository.getUserAnalytic(newUserId);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(0, result.getCountRent());
        assertEquals(0, result.getCountOpenRent());
        assertEquals(0, result.getCountCloseRent());
        assertTrue(result.getOpenBook().isEmpty());
        assertTrue(result.getCloseBook().isEmpty());
    }

    @Test
    public void testGetUserAnalytic_OnlyOpenRent() {
        // Добавляем новый открытый BookRent
        BookRent newRent = new BookRent();
        newRent.setUser(testUser);
        newRent.setRentStatus(BookRent.RentStatus.OPENED);
        newRent.setRentDate(LocalDateTime.now().minusDays(3));

        em.getTransaction().begin();
        em.persist(newRent);
        em.getTransaction().commit();

        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        when(userRepository.findById(userId)).thenReturn(testUser);

        UserAnalytic result = repository.getUserAnalytic(userId, startTime, endTime);

        assertNotNull(result);
        assertEquals(2, result.getCountRent());
        assertEquals(2, result.getCountOpenRent());
        assertEquals(0, result.getCountCloseRent());
    }

    @AfterEach
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BookRent").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
