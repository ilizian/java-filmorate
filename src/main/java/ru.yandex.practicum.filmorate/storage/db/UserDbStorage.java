package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) throws ValidationException {
        validateUser(user);
        String sql = "INSERT INTO users (email, login, user_name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[]{"user_id"});
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            return preparedStatement;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User updateUser(User user) throws NotFoundException, ValidationException {
        validateUser(user);
        String sql = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ? WHERE user_id = ?";
        int result = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (result == 0) {
            throw new NotFoundException("Ошибка. Неправильный id пользователя");
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::makeUser);
    }

    @Override
    public void validateUser(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Неправильный логин");
        }
    }

    @Override
    public User getUserById(long id) throws NotFoundException {
        User user;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, this::makeUser, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Пользователь по ID " + id + " не найден!");
        }
        return user;
    }

    private User makeUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("user_name"),
                resultSet.getDate("birthday").toLocalDate()
        );
    }

}
