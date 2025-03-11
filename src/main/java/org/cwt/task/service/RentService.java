package org.cwt.task.service;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.model.entity.BookRent;

import java.util.List;
import java.util.UUID;

public interface RentService {
    BookRent takeRent(BookRentDto rent, Long bookId, UUID userId);

    void returnRent(UUID id);

    List<BookRent> getRentList();

    List<BookRent> getRentList(UUID userId);
}
