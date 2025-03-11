package org.cwt.task.service;

import org.cwt.task.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(Long id);

    Book save(Book book);

    Book update(Long id, Book book);

    void deleteById(Long id);
}
