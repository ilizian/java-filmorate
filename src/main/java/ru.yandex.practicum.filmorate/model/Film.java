package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotEmpty(message = "Неправильное название")
    private String name;
    @Size(max = 200, message = "Неправильное описание")
    private String description;
    @PastOrPresent(message = "Неправильная дата релиза")
    private LocalDate releaseDate;
    @Min(value = 1, message = "Неправильная продолжительность")
    private long duration;
}
