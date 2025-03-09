package org.cwt.task.service;

import org.cwt.task.entity.BookRent;

import java.util.List;

public interface RentService {
    void takeRent(BookRent rent);

    void returnRent(BookRent rent);

    List<BookRent> getRentList();
}
