package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;

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
        return jdbcTemplate.queryForObject("SELECT name FROM genre WHERE genre_id = ?", genreRowMapper, id);
    }

    @Override
    public ArrayList<Genre> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    private final RowMapper<Genre> genreRowMapper = (recordSet, rowNumber) -> Genre.builder()
            .id(recordSet.getInt("genre_id"))
            .name(recordSet.getString("name"))
            .build();

}
