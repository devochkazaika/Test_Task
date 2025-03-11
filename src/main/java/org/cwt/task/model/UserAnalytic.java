package org.cwt.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnalytic {
    String firstName;
    String lastName;

    Integer countRent;
    Integer countOpenRent;
    Integer countCloseRent;

    List<String> openBook;
    List<String> closeBook;

}
