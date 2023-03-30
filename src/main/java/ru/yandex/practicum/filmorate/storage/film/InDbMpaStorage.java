package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class InDbMpaStorage implements Storage<Mpa> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa create(Mpa mpa) {
        return null;
    }

    @Override
    public Mpa update(Mpa mpa) {
        return null;
    }

    @Override
    public Mpa find(long id) {
        if (id < 1) return null;
        return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE mpa_id = ?", mpaRowMapper, id);
    }

    @Override
    public ArrayList<Mpa> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM mpa", mpaRowMapper));
    }

    @Override
    public void delete(long id) {}

    @Override
    public boolean isExist(long id) {
        try {
            jdbcTemplate.queryForObject("SELECT mpa_id FROM mpa WHERE mpa_id = ?", Long.class, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final RowMapper<Mpa> mpaRowMapper = (recordSet, rowNumber) -> Mpa.builder()
            .id(recordSet.getInt("mpa_id"))
            .name(recordSet.getString("name"))
            .build();

}
