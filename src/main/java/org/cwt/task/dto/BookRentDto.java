package org.cwt.task.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.cwt.task.model.entity.BookRent;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class BookRentDto {

    @NotNull(message = "Дата аренды не может быть пустой")
    @PastOrPresent(message = "Дата аренды не может быть в будущем")
    LocalDateTime rentDate;

    @NotNull(message = "Дата возврата не может быть пустой")
    @FutureOrPresent(message = "Дата возврата должна быть в будущем или равна текущей")
    LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    BookRent.RentStatus rentStatus;

    @NotBlank(message = "Название книги не может быть пустым")
    String bookName;

    String firstName;
    String lastName;
}