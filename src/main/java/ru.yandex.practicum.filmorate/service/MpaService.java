package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.dal.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa getMpaById(int id) throws NotFoundException {
        Mpa mpa = mpaStorage.getMpaById(id);
        return mpa;
    }

    public List<Mpa> getAllMpa() {
        List<Mpa> mpas = mpaStorage.getAllMpa();
        return mpas;
    }

}
