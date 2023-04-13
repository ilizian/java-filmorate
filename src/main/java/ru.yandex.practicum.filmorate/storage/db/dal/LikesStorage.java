package ru.yandex.practicum.filmorate.storage.db.dal;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {
    void addUserLike(long id, long userId);

    void removeLike(long id, long userId);

    List<Film> getTopFilms(int count);
}
