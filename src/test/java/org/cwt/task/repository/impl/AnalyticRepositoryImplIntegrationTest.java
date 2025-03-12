package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.model.entity.Book;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticRepositoryImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private AnalyticRepositoryImpl repository;
    private UserRepository userRepository;

    private UUID userId;
    private User testUser;
    private UUID bookRentId;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();

        userRepository = new UserRepositoryImpl(em);
        repository = new AnalyticRepositoryImpl(em, userRepository);
        BookRentRepositoryImpl bookRentRepository = new BookRentRepositoryImpl(em);
        BookRepository bookRepository = new BookRepositoryImpl(em);

        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");

        em.getTransaction().begin();
        em.persist(testUser);
        em.getTransaction().commit();

        userId = testUser.getId();

        BookRent bookRent = new BookRent();
        bookRent.setUser(testUser);
        bookRent.setRentStatus(BookRent.RentStatus.CLOSED);
        bookRent.setRentDate(LocalDateTime.now().minusDays(5));
        bookRent.setReturnDate(LocalDateTime.now().minusDays(2));

        em.getTransaction().begin();
        em.persist(bookRent);
        em.getTransaction().commit();

        Book testBook = new Book();
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setCount(20);

        testBook = bookRepository.save(testBook);
        bookRentId = bookRent.getId();
        userRepository.save(testUser);
        bookRentRepository.takeRent(bookRent, testBook.getId());
    }

    @Test
    public void testGetBooksByStatusCLOSED() {
        List<String> books = repository.getBooksByStatus(userId, BookRent.RentStatus.CLOSED, LocalDateTime.now().minusDays(10), LocalDateTime.now());

        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertTrue(books.contains("Test Book"));
    }

    @Test
    public void testGetUserAnalytic() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        UserAnalytic analytic = repository.getUserAnalytic(userId, startTime, endTime);

        assertNotNull(analytic);
        assertEquals("John", analytic.getFirstName());
        assertEquals("Doe", analytic.getLastName());
        assertEquals(1, analytic.getCountRent());
        assertEquals(0, analytic.getCountOpenRent());
        assertEquals(1, analytic.getCountCloseRent());
    }

    @Test
    public void testGetUserAnalyticWithoutEndTime() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(10);

        UserAnalytic analytic = repository.getUserAnalytic(userId, startTime);

        assertNotNull(analytic);
        assertEquals("John", analytic.getFirstName());
        assertEquals("Doe", analytic.getLastName());
        assertEquals(1, analytic.getCountRent());
        assertEquals(0, analytic.getCountOpenRent());
        assertEquals(1, analytic.getCountCloseRent());
    }

    @Test
    public void testGetUserAnalyticWithNoRent() {
        // Create a user with no rents
        User noRentUser = new User();
        noRentUser.setFirstName("Jane");
        noRentUser.setLastName("Doe");
        noRentUser.setEmail("jane.doe@example.com");

        em.getTransaction().begin();
        em.persist(noRentUser);
        em.getTransaction().commit();

        UUID noRentUserId = noRentUser.getId();

        LocalDateTime startTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endTime = LocalDateTime.now();

        UserAnalytic analytic = repository.getUserAnalytic(noRentUserId, startTime, endTime);

        assertNotNull(analytic);
        assertEquals("Jane", analytic.getFirstName());
        assertEquals("Doe", analytic.getLastName());
        assertEquals(0, analytic.getCountRent());
        assertEquals(0, analytic.getCountOpenRent());
        assertEquals(0, analytic.getCountCloseRent());
    }

    @AfterEach
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BookRent").executeUpdate(); // Cleaning up BookRent entities
            em.createQuery("DELETE FROM User").executeUpdate();   // Cleaning up User entities
            em.getTransaction().commit();
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
