package org.cwt.task.repository;

import org.cwt.task.model.entity.BookRent;

import java.util.List;
import java.util.UUID;

public interface BookRentRepository {
    BookRent save(BookRent bookRent);

    List<BookRent> findAll();

    BookRent findById(UUID id);

    List<BookRent> findByUserId(UUID userId);

    BookRent takeRent(BookRent bookRent, Long bookId);

    BookRent finalRent(UUID bookRent);
}
