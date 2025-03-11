package org.cwt.task.model;

import lombok.*;

import java.util.List;

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

    List<String> openBook;
    List<String> closeBook;

}
