package org.cwt.task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
}
