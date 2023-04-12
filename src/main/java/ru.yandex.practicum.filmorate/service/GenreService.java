package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    public GenreDbStorage genreDbStorage;

    public Genre getGenreById(int id) throws NotFoundException {
        return genreDbStorage.getGenreById(id);
    }

    public Collection<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }
}
