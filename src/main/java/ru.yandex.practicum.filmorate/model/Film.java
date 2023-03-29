package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    int id;
    @NotBlank
    String name;
    @Size(min = 1, max = 200)
    String description;
    LocalDate releaseDate;
    @Min(1)
    long duration;
}
