package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    private static Validator validator;

    @BeforeEach
    void initial() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void addFilmWrongName() {
        Film film = new Film();
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
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(100);
        film.setDescription("ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильное описание", validate.getMessage());
    }

    @Test
    void addFilmWrongDuration() {
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(-1);
        film.setDescription("Описание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(1999, 1, 1));
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильная продолжительность", validate.getMessage());
    }

    @Test
    void addFilmWrongDateFuture() {
        Film film = new Film();
        film.setName("Тестовый фильм");
        film.setDuration(100);
        film.setDescription("Описание");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(2100, 1, 1));
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        assertEquals(1, validates.size());
        ConstraintViolation<Film> validate = validates.iterator().next();
        assertEquals("Неправильная дата релиза", validate.getMessage());
    }

    @Test
    void addFilmWrongDatePastMin() {
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
}
