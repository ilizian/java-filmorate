package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.dal.LikesStorage;

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
    private final LikesStorage likesStorage;

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
        User user = userStorage.getUserById(userId);
        likesStorage.addUserLike(film.getId(), user.getId());
        return film;
    }

    public Film removeUserLike(long id, long userId) throws NotFoundException {
        Film film = filmStorage.getFilmById(id);
        User user = userStorage.getUserById(userId);
        likesStorage.removeLike(film.getId(), user.getId());
        return film;
    }

    public Collection<Film> getTopFilms(int count) {
        Collection<Film> films = likesStorage.getTopFilms(count);
        return films;
    }


}
