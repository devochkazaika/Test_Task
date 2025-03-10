package org.cwt.task.service.impl;

import org.cwt.task.entity.Book;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.service.BookService;

import javax.inject.Inject;
import java.util.List;

public class BookServiceImpl implements BookService {
    @Inject
    private BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
