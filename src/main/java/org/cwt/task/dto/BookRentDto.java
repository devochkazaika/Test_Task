package org.cwt.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @NotBlank(message = "Название книги не может быть пустым")
    String bookName;

    String firstName;
    String lastName;
}