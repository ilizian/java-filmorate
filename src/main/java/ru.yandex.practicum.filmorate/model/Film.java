package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {
    private long id;
    @NotEmpty(message = "Неправильное название")
    private String name;
    @Size(max = 200, message = "Неправильное описание")
    private String description;
    @PastOrPresent(message = "Неправильная дата релиза")
    private LocalDate releaseDate;
    @Min(value = 1, message = "Неправильная продолжительность")
    private long duration;
    private final List<Long> likes = new ArrayList<>();
}
