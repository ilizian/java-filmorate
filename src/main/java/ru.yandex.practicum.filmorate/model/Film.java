package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @Min(1)
    private long duration;
}
