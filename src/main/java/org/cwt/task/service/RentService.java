package org.cwt.task.service;

import org.cwt.task.model.entity.BookRent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentService {
    BookRent takeRent(LocalDateTime rent, Long bookId, UUID userId);

    void returnRent(UUID id);

    List<BookRent> getRentList();

    List<BookRent> getRentList(UUID userId);
}
