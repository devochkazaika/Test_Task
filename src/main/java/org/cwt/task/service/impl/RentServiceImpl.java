package org.cwt.task.service.impl;

import jakarta.inject.Inject;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.service.RentService;
import org.cwt.task.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RentServiceImpl implements RentService {
    @Inject
    private BookRentRepository repository;

    @Inject
    private UserService userService;

    @Override
    public BookRent takeRent(LocalDateTime rentDate, Long bookId, UUID userId) {
        BookRent rentEntity = new BookRent();
        rentEntity.setRentDate(Objects.requireNonNullElseGet(rentDate, LocalDateTime::now));
        rentEntity.setRentStatus(BookRent.RentStatus.OPENED);

        User user = userService.getUser(userId);
        rentEntity.setUser(user);

        return repository.takeRent(rentEntity, bookId);
    }

    @Override
    public void returnRent(UUID id) {
        repository.finalRent(id);
    }

    @Override
    public List<BookRent> getRentList() {
        return repository.findAll();
    }

    @Override
    public List<BookRent> getRentList(UUID userId) {
        // Для проверки существует ли user
        userService.getUser(userId);

        return repository.findByUserId(userId);
    }
}
