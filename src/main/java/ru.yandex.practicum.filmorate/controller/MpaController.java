package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
@Validated
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> getAllMpa() {
        log.info("Запрос списка рейтинга MPA");
        return mpaService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) throws NotFoundException {
        log.info("Запрос MPA по id " + id);
        return mpaService.getMpaById(id);
    }
}
