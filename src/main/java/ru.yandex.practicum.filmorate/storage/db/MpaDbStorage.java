package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.dal.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getMpaById(int id) throws NotFoundException {
        Mpa mpa;
        String sql = "SELECT * FROM mpas WHERE mpa_id = ?";
        try {
            mpa = jdbcTemplate.queryForObject(sql, this::makeMpa, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("MPA по ID " + id + " не найден!");
        }
        return mpa;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM mpas";
        return jdbcTemplate.query(sql, this::makeMpa);
    }

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name"));
    }
}
