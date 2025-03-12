package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.cwt.task.exception.NotFoundException;
import org.cwt.task.model.entity.Book;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.repository.BookRentRepository;

import java.util.List;
import java.util.UUID;

@Singleton
public class BookRentRepositoryImpl implements BookRentRepository {
    private EntityManager entityManager;

    @Inject
    public BookRentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BookRent findById(UUID id) {
        try {
            return entityManager
                    .createQuery("Select r from BookRent r where r.uuid = :id", BookRent.class)
                    .setParameter("id", id).getSingleResult();
        }
        catch (NoResultException ex){
            throw new NotFoundException("Book not found with id = " + id);
        }
    }

    @Override
    public List<BookRent> findAll() {
        return entityManager.createQuery("from BookRent", BookRent.class).getResultList();
    }

    @Override
    public BookRent save(BookRent bookRent) {
        try {
            entityManager.getTransaction().begin();
            Book book = entityManager.createQuery("select b from Book b where b.id = :bookId", Book.class)
                    .setParameter("bookId", bookRent.getBook().getId())
                    .getSingleResult();
            if (book.getCount() <= 0) throw new IllegalArgumentException("Book is zero count");
            book.setCount(book.getCount() - 1);
            entityManager.persist(book);
            entityManager.persist(bookRent);
            entityManager.getTransaction().commit();
            return bookRent;
        }
        catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<BookRent> findByUserId(UUID userId) {
        try {
            return entityManager
                    .createQuery("Select r from BookRent r where r.user.id = :userId", BookRent.class)
                    .setParameter("userId", userId).getResultList();
        }
        catch (NoResultException ex){
            throw new NotFoundException("Book not found with user id = " + userId);
        }
    }
}
