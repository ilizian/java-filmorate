package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
@Validated
public class GenreController {
    private final GenreService genreService;
    @GetMapping
    public Collection<Genre> getAllGenres() {
        log.info("Запрос списка всех жанров");
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenreById (@PathVariable Integer id) throws NotFoundException {
        log.info("Запрос жанра по id " + id);
        return genreService.getGenreById(id);
    }
}
