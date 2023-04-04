package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("Запрос списка всех пользователей");
        return userService.getAllUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        userService.addUser(user);
        log.info("Добавлен новый пользователь " + user.getLogin());
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        userService.updateUser(user);
        log.info("Обновлен пользователь " + user.getName());
        return user;
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) throws NotFoundException {
        log.info("Запрос списка друзей пользователя по id " + id);
        return userService.getUserFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addUserFriend(@PathVariable long id, @PathVariable long friendId) throws NotFoundException {
        log.info("Добавление друга пользователю по id " + id + " friendId " + friendId);
        return userService.addUserFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeUserFriend(@PathVariable long id, @PathVariable long friendId) throws NotFoundException {
        log.info("Удаление друга пользователю по id " + id + " friendId " + friendId);
        return userService.removeUserFriend(id, friendId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws NotFoundException {
        log.info("Запрос пользователя по id " + id);
        return userService.getUserById(id);
    }

    @GetMapping("/{id1}/friends/common/{id2}")
    public List<User> getCommonFriends(@PathVariable long id1, @PathVariable long id2) throws NotFoundException {
        log.info("Запрос списка общих друзей пользователей по id " + id1 + ", " + id2);
        return userService.getCommonFriends(id1, id2);
    }

}
