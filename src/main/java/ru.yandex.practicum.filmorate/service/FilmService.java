package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    public Film addFilm(Film film) throws ValidationException {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long id) throws NotFoundException {
        return filmStorage.getFilmById(id);
    }

    public Film addUserLike(long id, long userId) throws NotFoundException {
        Film film = filmStorage.getFilmById(id);
        userStorage.getUserById(userId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeUserLike(long id, long userId) throws NotFoundException {
        Film film = filmStorage.getFilmById(id);
        userStorage.getUserById(userId);
        if (!film.getLikes().contains(userId)) {
            throw new NotFoundException("Ошибка. Неправильный id фильма");
        }
        film.getLikes().remove(userId);
        return film;
    }

    public Collection<Film> getTopFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing(f -> f.getLikes().size(), (f1, f2) -> f2 - f1))
                .limit(count)
                .collect(Collectors.toList());
    }


}
