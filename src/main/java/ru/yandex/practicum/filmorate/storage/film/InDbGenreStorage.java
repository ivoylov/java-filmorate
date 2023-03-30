package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class InDbGenreStorage implements Storage<Genre> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public Genre find(long id) {
        if (id < 1) {
            return null;
        }
        return jdbcTemplate.queryForObject("SELECT * FROM genre WHERE genre_id = ?", genreRowMapper, id);
    }

    public ArrayList<Genre> findAll(long id) {
        if (id < 1) {
            return new ArrayList<>();
        }
        return new ArrayList<>(List.of(jdbcTemplate.queryForObject("SELECT * FROM genre WHERE genre_id = ?", genreRowMapper, id)));
    }

    @Override
    public ArrayList<Genre> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM genre", genreRowMapper));
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean isExist(long id) {
        try {
            jdbcTemplate.queryForObject("SELECT genre_id FROM genre WHERE genre_id = ?", Long.class, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final RowMapper<Genre> genreRowMapper = (recordSet, rowNumber) -> Genre.builder()
            .id(recordSet.getInt("genre_id"))
            .name(recordSet.getString("name"))
            .build();

}
