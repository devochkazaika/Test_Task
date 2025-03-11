package org.cwt.task.service.impl;

import jakarta.inject.Inject;
import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.cwt.task.entity.User;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.service.BookService;
import org.cwt.task.service.RentService;
import org.cwt.task.service.UserService;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RentServiceImpl implements RentService {
    @Inject
    private BookRentRepository repository;

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public BookRent takeRent(BookRentDto rent, Long bookId, UUID userId) {
        BookRent rentEntity = modelMapper.map(rent, BookRent.class);
        rentEntity.setRentStatus(BookRent.RentStatus.OPENED);
        rentEntity.setBook(bookService.getById(bookId));

        User user = userService.getUser(userId);
        rentEntity.setUser(user);

        return repository.save(rentEntity);
    }

    @Override
    public void returnRent(UUID id) {
        BookRent bookRent = repository.findById(id);
        bookRent.setReturnDate(LocalDateTime.now());
        bookRent.setRentStatus(BookRent.RentStatus.CLOSED);

        repository.save(bookRent);
    }

    @Override
    public List<BookRent> getRentList() {
        return repository.findAll();
    }

    @Override
    public List<BookRent> getRentList(UUID userId) {
        return repository.findByUserId(userId);
    }
}
