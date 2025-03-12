package org.cwt.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.cwt.task.model.entity.BookRent;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class BookRentDto {
    @JsonIgnore
    UUID id;

    @NotNull(message = "Дата аренды не может быть пустой")
    LocalDateTime rentDate;

    @NotNull(message = "Дата возврата не может быть пустой")
    LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    BookRent.RentStatus rentStatus;

    String bookName;

    String firstName;
    String lastName;

    UUID userId;
}
