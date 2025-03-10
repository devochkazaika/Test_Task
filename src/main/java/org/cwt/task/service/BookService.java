package org.cwt.task.service;

import org.cwt.task.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(Long id);

    Book save(Book book);

    Book update(Book book);

    void deleteById(Long id);
}
