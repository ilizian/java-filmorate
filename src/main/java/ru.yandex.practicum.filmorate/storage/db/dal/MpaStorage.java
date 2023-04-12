package ru.yandex.practicum.filmorate.storage.db.dal;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;

public interface MpaStorage {
    Mpa getMpaById(int id) throws NotFoundException;

    List<Mpa> getAllMpa();
}
