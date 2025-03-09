package org.cwt.task.repository;

import org.cwt.task.entity.Book;

import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    void delete(Long bookId);
}
