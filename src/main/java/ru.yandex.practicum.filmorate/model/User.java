package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private long id;
    @NotNull(message = "Неправильный email")
    @Email(message = "Неправильный email")
    private String email;
    @NotBlank(message = "Неправильный логин")
    private String login;
    private String name;
    @Past(message = "Неправильная дата рождения")
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
}
