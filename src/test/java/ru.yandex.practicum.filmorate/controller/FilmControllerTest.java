package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;


import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    private static Validator validator;
    private static InMemoryFilmStorage filmStorage;

    @BeforeEach
    void initial() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        filmStorage = new InMemoryFilmStorage();
    }

    @Test
    void addFilmWrongName() {
        Film film = new Film(1, "", "Desc", LocalDate.of(1999, 1, 1),
                100, new Mpa(1, "name"), 5);
        film.setDuration(100);
        film.setDescription("Описание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильное название", validate.getMessage());
    }

    @Test
    void addFilmWrongDescription() {
        Film film = new Film(2, "Name", "Desc", LocalDate.of(1999, 1, 1),
                100, new Mpa(1, "name"), 5);
        film.setDescription("ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание");
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильное описание", validate.getMessage());
    }

    @Test
    void addFilmWrongDuration() {
        Film film = new Film(3, "Name", "Desc", LocalDate.of(1999, 1, 1),
                100, new Mpa(1, "name"), 5);
        film.setDuration(-1);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильная продолжительность", validate.getMessage());
    }

    @Test
    void addFilmWrongDateFuture() {
        Film film = new Film(3, "Name", "Desc", LocalDate.of(2100, 1, 1),
                100, new Mpa(1, "name"), 5);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильная дата релиза", validate.getMessage());
    }

    @Test
    void addFilmWrongDatePastMin() {
        Film film = new Film(3, "Name", "Desc", LocalDate.of(1600, 1, 1),
                100, new Mpa(1, "name"), 5);
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> filmStorage.addFilm(film));
        assertEquals("Ошибка. Неправильная дата релиза", e.getMessage());
    }
}
