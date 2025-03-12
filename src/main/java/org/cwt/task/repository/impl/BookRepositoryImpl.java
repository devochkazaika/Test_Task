package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.cwt.task.model.entity.Book;
import org.cwt.task.exception.NotFoundException;
import org.cwt.task.repository.BookRepository;

import java.util.List;

@Singleton
public class BookRepositoryImpl implements BookRepository {
    private EntityManager em;

    @Inject
    public BookRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

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
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        }
        catch (Exception e){
            throw new RuntimeException("Could not save book", e);
        }
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
