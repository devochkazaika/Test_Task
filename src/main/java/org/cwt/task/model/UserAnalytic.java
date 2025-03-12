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

    // Оборот книг у пользователя
    Integer countRent;

    // Количество выданных, но на момент временного интервала не возвращенных книг
    Integer countOpenRent;

    // Количество возвращенных книг на временном интервале
    Integer countCloseRent;

    // Выданные книги на временном интервале
    List<String> openBook;

    // Возвращенные книги на временном интервале
    List<String> closeBook;

}
