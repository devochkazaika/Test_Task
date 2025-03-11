package org.cwt.task.dto;

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
public class OpenRentDto {
    @NotNull(message = "Дата аренды не может быть пустой")
    @PastOrPresent(message = "Дата аренды не может быть в будущем")
    LocalDateTime rentDate;
}