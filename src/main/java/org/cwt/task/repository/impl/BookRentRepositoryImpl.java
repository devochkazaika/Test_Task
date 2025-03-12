package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.cwt.task.exception.NotFoundException;
import org.cwt.task.model.entity.Book;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.repository.BookRentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Singleton
public class BookRentRepositoryImpl implements BookRentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public BookRentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BookRent findById(UUID id) {
        try {
            return entityManager
                    .createQuery("Select r from BookRent r where r.id = :id", BookRent.class)
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
    public BookRent takeRent(BookRent bookRent, Long bookId) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SET TRANSACTION ISOLATION LEVEL REPEATABLE READ").executeUpdate();

            Book book = entityManager.find(Book.class, bookId);
            if (book.getCount() <= 0) throw new IllegalArgumentException("Book count is 0");
            book.setCount(book.getCount() - 1);

            entityManager.persist(book);
            bookRent.setBook(book);
            entityManager.persist(bookRent);
            entityManager.getTransaction().commit();
            return bookRent;
        }
        catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public BookRent finalRent(UUID id) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SET TRANSACTION ISOLATION LEVEL REPEATABLE READ").executeUpdate();

            BookRent bookRent = entityManager.find(BookRent.class, id);
            if (bookRent.getRentStatus() == BookRent.RentStatus.CLOSED){
                throw new IllegalArgumentException("Book Rent is already closed");
            }
            bookRent.setReturnDate(LocalDateTime.now());
            bookRent.setRentStatus(BookRent.RentStatus.CLOSED);
            Book book = entityManager.find(Book.class, bookRent.getBook().getId());
            book.setCount(book.getCount() + 1);

            entityManager.persist(book);
            bookRent.setBook(book);
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
