package org.cwt.task.repository.impl;

import jakarta.persistence.EntityManager;
import org.cwt.task.entity.BookRent;
import org.cwt.task.repository.BookRentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class BookRentRepositoryImpl implements BookRentRepository {
    @Inject
    private EntityManager entityManager;


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
