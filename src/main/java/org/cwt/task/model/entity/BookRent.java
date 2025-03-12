package org.cwt.task.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRent {
    @Id
    UUID id = UUID.randomUUID();

    LocalDateTime rentDate;

    LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @Enumerated(EnumType.STRING)
    RentStatus rentStatus;

    public enum RentStatus {
        OPENED,
        CLOSED
    }


}
