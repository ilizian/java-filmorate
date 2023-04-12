package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
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
    private final Set<Long> likes = new HashSet<>();
    private final Set<Genre> genres = new HashSet<>();
    private final Mpa mpa;
}
