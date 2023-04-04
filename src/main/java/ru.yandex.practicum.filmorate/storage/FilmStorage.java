package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film) throws ValidationException;

    Film updateFilm(Film film) throws ValidationException, NotFoundException;

    void validateFilm(Film film) throws ValidationException;

    Collection<Film> getAllFilms();

    Film getFilmById(long id) throws NotFoundException;
}
