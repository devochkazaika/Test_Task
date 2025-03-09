package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import org.cwt.task.entity.Book;
import org.cwt.task.repository.BookRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BookRepositoryImpl implements BookRepository {
    @Inject
    private EntityManager em;

    @Override
    public Book save(Book book) {
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return books;
    }

    @Override
    public void deleteById(Long bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();
        }
    }
}
