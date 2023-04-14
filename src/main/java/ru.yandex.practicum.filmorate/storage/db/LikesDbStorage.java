package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.dal.LikesStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addUserLike(long id, long userId) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?);" +
                "UPDATE films SET rate = rate + 1 WHERE films.film_id = ?";
        jdbcTemplate.update(sql, id, userId, id);
    }

    @Override
    public void removeLike(long id, long userId) {
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?;" +
                "UPDATE films SET rate = rate - 1 WHERE films.film_id = ?";
        jdbcTemplate.update(sql, id, userId, id);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        final String sqlQuery = "SELECT * FROM films, mpas WHERE films.mpa_id = mpas.mpa_id ORDER BY rate DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, count);
    }


    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return new Film(
                resultSet.getInt("film_id"),
                resultSet.getString("film_name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getInt("duration"),
                new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name")),
                resultSet.getInt("rate")
        );
    }
}
