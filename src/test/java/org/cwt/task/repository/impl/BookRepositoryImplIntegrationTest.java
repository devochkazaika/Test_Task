package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.model.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private BookRepositoryImpl repository;

    private Long bookId;
    private Book testBook;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();

        repository = new BookRepositoryImpl(em);

        testBook = new Book();
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");

        em.getTransaction().begin();
        em.persist(testBook);
        em.getTransaction().commit();

        bookId = testBook.getId();
    }

    @Test
    public void testFindById() {
        Book foundBook = repository.findById(bookId);

        assertNotNull(foundBook);
        assertEquals(bookId, foundBook.getId());
        assertEquals("Test Book", foundBook.getName());
    }

    @Test
    public void testFindAll() {
        List<Book> result = repository.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(bookId, result.get(0).getId());
    }

    @Test
    public void testSave() {
        Book newBook = new Book();
        newBook.setName("New Book");
        newBook.setAuthor("New Author");

        repository.save(newBook);
        Book foundBook = repository.findById(newBook.getId());

        assertNotNull(foundBook);
        assertEquals("New Book", foundBook.getName());
        assertEquals("New Author", foundBook.getAuthor());
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(bookId);

        em.getTransaction().begin();
        Book deletedBook = em.find(Book.class, bookId);
        em.getTransaction().commit();

        assertNull(deletedBook);
    }

    @Test
    public void testFindByIdNotFound() {
        Long nonExistentId = 9999L;
        assertThrows(org.cwt.task.exception.NotFoundException.class, () -> repository.findById(nonExistentId));
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
