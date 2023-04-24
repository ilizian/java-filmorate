package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private static final LocalDate DATE_MIN = LocalDate.of(1895, 12, 28);
    private long generateId = 0;

    @Override
    public Film addFilm(Film film) throws ValidationException {
        validateFilm(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }
        throw new NotFoundException("Ошибка. Неправильный id фильма");
    }

    @Override
    public void validateFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(DATE_MIN)) {
            throw new ValidationException("Ошибка. Неправильная дата релиза");
        }
    }

    @Override
    public Collection<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(long id) throws NotFoundException {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new NotFoundException("Ошибка. Невозможно получить фильм с id " + id);
    }

    private long generateId() {
        return ++generateId;
    }
}
