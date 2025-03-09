package org.cwt.task.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRentDto {
    LocalDateTime rentDate;

    LocalDateTime returnDate;
}
