package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final LocalDate DATE_MIN = LocalDate.of(1895, 12, 28);

    @Override
    public Film addFilm(Film film) throws ValidationException {
        validateFilm(film);
        String sql = "INSERT INTO films (film_name, description, release_date, duration, mpa_id, rate)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[]{"film_id"});
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            preparedStatement.setInt(4, (int) film.getDuration());
            preparedStatement.setInt(5, film.getMpa().getId());
            preparedStatement.setInt(6, film.getRate());
            return preparedStatement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        saveFilmGenres(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        validateFilm(film);
        String sql = "UPDATE films SET film_name = ?, description = ?, release_date = ?, " +
                "duration = ?,  mpa_id = ?, rate = ? " +
                "WHERE film_id = ?";
        int result = jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getRate(), film.getId());
        if (result == 0) {
            throw new NotFoundException("Ошибка. Неправильный id фильма");
        }
        saveFilmGenres(film);
        return film;
    }

    @Override
    public void validateFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(DATE_MIN)) {
            throw new ValidationException("Ошибка. Неправильная дата релиза");
        }
    }

    @Override
    public Collection<Film> getAllFilms() {
        String sql = "SELECT * FROM films JOIN mpas ON mpas.mpa_id = films.mpa_id";
        return jdbcTemplate.query(sql, this::makeFilm);
    }

    @Override
    public Film getFilmById(long id) throws NotFoundException {
        String sql = "SELECT films.*, mpas.* FROM films JOIN mpas ON mpas.mpa_id = films.mpa_id WHERE film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::makeFilm, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Фильм по ID " + id + " не найден!");
        }
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film;
        film = new Film(
                resultSet.getInt("film_id"),
                resultSet.getString("film_name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getInt("duration"),
                new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name")),
                resultSet.getInt("rate")
        );
        return film;
    }

    private void saveFilmGenres(Film film) {
        long id = film.getId();
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", id);
        final Set<Genre> genres = film.getGenres();
        final ArrayList<Genre> genreList = new ArrayList<>(genres);
        jdbcTemplate.batchUpdate("INSERT INTO film_genre (film_id, genre_id) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, id);
                        ps.setInt(2, genreList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }
}
