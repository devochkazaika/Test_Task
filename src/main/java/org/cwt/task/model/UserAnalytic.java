package org.cwt.task.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnalytic {
    String firstName;
    String lastName;

    Integer countRent;
    Integer countOpenRent;
    Integer countCloseRent;

    List<String> openBook;
    List<String> closeBook;

}
