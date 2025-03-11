package org.cwt.task.model.entity;


import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    BookTheme bookTheme;

    public enum BookTheme {
        CLASSIC,
        FANTASTIC
    }
}
