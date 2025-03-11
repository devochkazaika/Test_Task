package org.cwt.task.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.cwt.task.entity.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 255, message = "Название книги не должно превышать 255 символов")
    String name;

    @NotBlank(message = "Автор книги не может быть пустым")
    @Size(max = 255, message = "Имя автора не должно превышать 255 символов")
    String author;

    @NotNull(message = "Дата публикации обязательна")
    @PastOrPresent(message = "Дата публикации не может быть в будущем")
    LocalDate publicationDate;

    @NotNull(message = "Тема книги обязательна")
    @Enumerated(EnumType.STRING)
    Book.BookTheme bookTheme;
}
