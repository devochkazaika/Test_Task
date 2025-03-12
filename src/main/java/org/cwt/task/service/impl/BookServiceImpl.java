package org.cwt.task.service.impl;

import jakarta.inject.Inject;
import org.cwt.task.model.entity.Book;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.service.BookService;
import org.modelmapper.ModelMapper;

import java.util.List;

public class BookServiceImpl implements BookService {
    @Inject
    private BookRepository bookRepository;

    @Inject
    private ModelMapper bookMapper;

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
        Book bookEntity = bookRepository.save(book);
        return bookEntity;
    }

    @Override
    public Book update(Long id, Book book) {
        Book bookEntity = bookRepository.findById(id);
        bookMapper.map(book, bookEntity);
        return bookRepository.save(bookEntity);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
