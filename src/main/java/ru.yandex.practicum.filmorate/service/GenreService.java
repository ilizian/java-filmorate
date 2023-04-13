package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    public Genre getGenreById(int id) throws NotFoundException {
        return genreDbStorage.getGenreById(id);
    }

    public Collection<Genre> getAllGenres() {
        Collection<Genre> genres = genreDbStorage.getAllGenres();
        return genres;
    }
}
