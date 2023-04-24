package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.dal.FriendsStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addUserFriend(long id, long friendId) throws NotFoundException {
        String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, id, friendId);
        if (result == 0) {
            throw new NotFoundException("Ошибка. Неправильный id пользователя");
        }
    }

    @Override
    public void removeUserFriend(long id, long friendId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getUserFriends(long id) {
        String sql = "SELECT * FROM users as u, friends as f WHERE u.user_id = f.friend_id AND f.user_id = ?";
        return jdbcTemplate.query(sql, this::makeUser, id);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        String sql = "SELECT * FROM users u, friends f, friends ff " +
                "WHERE u.user_id = f.friend_id AND u.user_id = ff.friend_id AND f.user_id = ? AND ff.user_id = ?";
        return jdbcTemplate.query(sql, this::makeUser, id, otherId);
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
