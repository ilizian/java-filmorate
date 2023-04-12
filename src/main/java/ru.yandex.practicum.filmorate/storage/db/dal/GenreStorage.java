package ru.yandex.practicum.filmorate.storage.db.dal;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Collection<Genre> getAllGenres();

    Genre getGenreById(int id) throws NotFoundException;
}
