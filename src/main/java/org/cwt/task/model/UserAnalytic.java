package org.cwt.task.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnalytic {
    String firstName;
    String lastName;

    Integer countRent;
    Integer countOpenRent;
    Integer countCloseRent;

    Set<String> openBook;
    Set<String> closeBook;

}
