package org.cwt.task.service.impl;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.cwt.task.entity.User;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.service.BookService;
import org.cwt.task.service.RentService;
import org.cwt.task.service.UserService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
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
        rentEntity.setBook(bookService.getById(bookId));
        User user = userService.getUser(userId);
        rentEntity.setUser(user);
        return repository.save(rentEntity);
    }

    @Override
    public void returnRent(BookRent rent) {

    }

    @Override
    public List<BookRent> getRentList() {
        return repository.findAll();
    }
}
