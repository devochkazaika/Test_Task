package org.cwt.task.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    UUID id = UUID.randomUUID();

    String firstName;

    String lastName;

    Byte age;

    @Column(unique = true)
    String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookRent> bookRents;
}
