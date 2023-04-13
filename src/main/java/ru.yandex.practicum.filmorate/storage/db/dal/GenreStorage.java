package ru.yandex.practicum.filmorate.storage.db.dal;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreStorage {
    Collection<Genre> getAllGenres();

    Genre getGenreById(int id) throws NotFoundException;
}
