package org.cwt.task.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cwt.task.entity.Book;
import org.cwt.task.repository.BookRepository;

import java.util.List;

@ApplicationScoped
public class BookRepositoryImpl implements BookRepository {

    private EntityManager em;
    private EntityManagerFactory entityManagerFactory;

    public BookRepositoryImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("library");
        em = entityManagerFactory.createEntityManager();

    }

    @Override
    public Book save(Book book) {
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        return book;
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public void delete(Long bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            em.remove(book);
        }
    }
}
