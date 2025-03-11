package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.cwt.task.entity.Book;
import org.cwt.task.exception.NotFoundException;
import org.cwt.task.repository.BookRepository;

import java.util.List;

@Singleton
public class BookRepositoryImpl implements BookRepository {
    @Inject
    private EntityManager em;

    @Override
    public Book findById(Long id) {
        try {
            return em.createQuery("SELECT b from Book b where b.id = :id", Book.class)
                    .setParameter("id", id).getSingleResult();
        }
        catch (NoResultException e){
            throw new NotFoundException("Book not found with id = " + id);
        }
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
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return books;
    }

    @Override
    public void deleteById(Long bookId) {
        Book book = findById(bookId);
        if (book != null) {
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();
        }
    }
}
