package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotEmpty
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
