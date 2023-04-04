package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long generateId = 0;

    @Override
    public User addUser(User user) throws ValidationException {
        validateUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationException, NotFoundException {
        validateUser(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }
        throw new NotFoundException("Ошибка. Неправильный id пользователя");
    }

    @Override
    public Collection<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void validateUser(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Неправильный логин");
        }
    }

    @Override
    public User getUserById(long id) throws NotFoundException {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new NotFoundException("Ошибка. Невозможно получить пользователя с id " + id);
    }

    private long generateId() {
        return ++generateId;
    }
}
