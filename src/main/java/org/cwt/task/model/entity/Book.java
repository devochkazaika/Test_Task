package org.cwt.task.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String author;

    LocalDate publicationDate;

    @Min(value = 0, message = "Количество книг не может быть отрицательным")
    Integer count;

    @Enumerated(EnumType.STRING)
    BookTheme bookTheme;

    public enum BookTheme {
        CLASSIC,
        FANTASTIC,
        EPIC,
        DRAMA,
        DETECTIVE
    }
}
