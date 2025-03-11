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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<BookRentDto> getRentList() {
        List<BookRentDto> listBook = new ArrayList<>();
        for (BookRent rentEntity : repository.findAll()) {
            BookRentDto dto = modelMapper.map(rentEntity, BookRentDto.class);
            listBook.add(dto);
        }
        return listBook;
    }
}
