package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final LocalDate dateMin = LocalDate.of(1895, 12, 28);
    private int generateId = 0;

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.debug("Запрос списка всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateFilm(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм " + film.getName());
        return film;
    }

    @PutMapping
    Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateFilm(film);
        if (film.getId() > 0) {
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                log.info("Обновление фильма " + film.getName());
                return film;
            }
        }
        log.error("Неправильный id фильма");
        throw new ValidationException("Ошибка. Неправильный id фильма");
    }

    private void validateFilm(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Неправильное название фильма");
            throw new ValidationException("Ошибка. Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Слишком длинное описание фильма");
            throw new ValidationException("Ошибка. Максимальная длина описания — 200 символов. Сейчас " +
                    film.getDescription().length());
        }
        if (film.getReleaseDate().isBefore(dateMin)) {
            log.error("Неправильная дата релиза");
            throw new ValidationException("Ошибка. Неправильная дата релиза");
        }
        if (film.getDuration() < 0) {
            log.error("Неправильная продолжительность");
            throw new ValidationException("Ошибка. Продолжительность фильма должна быть положительной");
        }
    }

    private int generateId() {
        return ++generateId;
    }
}
