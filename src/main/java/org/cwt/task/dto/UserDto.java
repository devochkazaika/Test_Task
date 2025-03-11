package org.cwt.task.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 255, message = "Фамилия не должна превышать 255 символов")
    String lastName;

    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 0, message = "Возраст должен быть не меньше 0")
    @Max(value = 120, message = "Возраст не может превышать 120 лет")
    Byte age;
}
