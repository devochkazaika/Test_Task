package org.cwt.task.mapper;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;

@Mapper
public interface BookRentMapper {
    BookRent bookRent(BookRentDto bookRentDto);
}
