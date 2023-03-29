package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int generateId = 0;

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("Запрос списка всех пользователей");
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь " + user.getLogin());
        return user;
    }

    @PutMapping
    User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        if (user.getId() > 0) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.info("Обновлен пользователь " + user.getName());
                return user;
            }
        }
        log.error("Неправильный id пользователя");
        throw new ValidationException("Ошибка. Неправильный id пользователя");
    }

    private void validateUser(@NonNull User user) throws ValidationException {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Неправильная электронная почта пользователя");
            throw new ValidationException("Ошибка. Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error("Неправильный логин пользователя");
            throw new ValidationException("Ошибка. Логин пользователя не должен быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Неправильная дата рождения пользователя");
            throw new ValidationException("Ошибка. Дата рождения не может быть в будущем");
        }
    }

    private int generateId() {
        return ++generateId;
    }
}