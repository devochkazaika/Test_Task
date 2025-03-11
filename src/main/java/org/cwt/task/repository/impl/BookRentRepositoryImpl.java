package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.cwt.task.entity.BookRent;
import org.cwt.task.repository.BookRentRepository;

import java.util.List;
import java.util.UUID;

@Singleton
public class BookRentRepositoryImpl implements BookRentRepository {
    @Inject
    private EntityManager entityManager;

    @Override
    public BookRent findById(UUID id) {
        return entityManager
                .createQuery("Select r from BookRent r where r = :id", BookRent.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<BookRent> findAll() {
        return entityManager.createQuery("from BookRent", BookRent.class).getResultList();
    }

    @Override
    public BookRent save(BookRent bookRent) {
        entityManager.getTransaction().begin();
        entityManager.persist(bookRent);
        entityManager.getTransaction().commit();
        return bookRent;
    }

    @Override
    public List<BookRent> getBookRentsByUserId(UUID userId) {
        return null;
    }
}
