package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;


    @GetMapping
    public Collection<Film> getAllFilms() {
        log.debug("Запрос списка всех фильмов");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        filmService.addFilm(film);
        log.info("Добавлен новый фильм " + film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException, NotFoundException {
        filmService.updateFilm(film);
        log.info("Обновление фильма " + film.getName());
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) throws NotFoundException {
        log.info("Запрос фильма по id " + id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addUserLike(@PathVariable long id, @PathVariable long userId) throws NotFoundException {
        log.info("Добавление лайка фильму по id  " + id);
        return filmService.addUserLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeUserLike(@PathVariable long id, @PathVariable long userId) throws NotFoundException {
        log.info("Удаление лайка фильму по id  " + id + " userId " + userId);
        return filmService.removeUserLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        log.info("Запрос топ - " + count + " фильмов");
        return filmService.getTopFilms(count);
    }

}
