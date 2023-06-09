package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.dal.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, this::makeGenre);
    }

    @Override
    public Genre getGenreById(int id) throws NotFoundException {
        String sql = "SELECT genre_id, genre_name FROM genres WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::makeGenre, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Жанр по ID " + id + " не найден!");
        }
    }

    @Override
    public void load(Collection<Film> films) {
        Map<Long, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));
        String sql = String.join(",", Collections.nCopies(films.size(), "?"));
        final String sqlQuery = "SELECT * FROM genres g, film_genre fg WHERE fg.genre_id = g.genre_id " +
                "AND fg.film_id IN (" + sql + ")";
        jdbcTemplate.query(sqlQuery, (rs) -> {
            Film film = filmById.get(rs.getLong("film_id"));
            film.getGenres().add(makeGenre(rs, 0));
        }, films.stream().map(Film::getId).toArray());
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_name"));
    }
}
