package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    @Test
    void addFilmWrongName() {
        Film film = new Film();
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    FilmController filmController = new FilmController();
                    filmController.addFilm(film);
                });
        assertEquals("Ошибка. Название не может быть пустым", e.getMessage());
    }

    @Test
    void addFilmWrongDate() {
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(100);
        film.setDescription("Описание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1600, 1, 1));
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    FilmController filmController = new FilmController();
                    filmController.addFilm(film);
                });
        assertEquals("Ошибка. Неправильная дата релиза", e.getMessage());
    }

    @Test
    void addFilmWrongDescription() {
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(100);
        film.setDescription("ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    FilmController filmController = new FilmController();
                    filmController.addFilm(film);
                });
        assertEquals("Ошибка. Максимальная длина описания — 200 символов. Сейчас 272", e.getMessage());
    }

    @Test
    void addFilmWrongDuration() {
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(-1);
        film.setDescription("Описание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    FilmController filmController = new FilmController();
                    filmController.addFilm(film);
                });
        assertEquals("Ошибка. Продолжительность фильма должна быть положительной", e.getMessage());
    }
}
