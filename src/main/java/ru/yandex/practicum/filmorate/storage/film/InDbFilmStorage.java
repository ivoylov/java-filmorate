package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class InDbFilmStorage implements Storage<Film> {

    private final JdbcTemplate jdbcTemplate;
    private InDbMpaStorage inDbMpaStorage;
    private InDbGenreStorage inDbGenreStorage;

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into film" +
                "(name, " +
                "description, " +
                "release_date, " +
                "duration, " +
                "mpa_id) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(getIdByName(film.getName()));
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE film SET " +
                "name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ?, " +
                "mpa_id = ?" +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public Film find(long id) {
        if (id < 1) return null;
        return jdbcTemplate.queryForObject("SELECT * FROM film WHERE film_id = ?", filmRowMapper, id);
    }

    @Override
    public ArrayList<Film> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM film", filmRowMapper));
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM film WHERE film_id = ?", id);
    }

    @Override
    public boolean isExist(long id) {
        try {
            jdbcTemplate.queryForObject("SELECT film_id FROM film WHERE film_id = ?", Long.class, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final RowMapper<Film> filmRowMapper = (recordSet, rowNumber) -> Film.builder()
            .id(recordSet.getLong("film_id"))
            .name(recordSet.getString("name"))
            .description(recordSet.getString("description"))
            .releaseDate(recordSet.getDate("release_date").toLocalDate())
            .duration(recordSet.getLong("duration"))
            .mpa(inDbMpaStorage.find(recordSet.getInt("mpa_id")))
            .genres(inDbGenreStorage.findAll(recordSet.getInt("genre_id")))
            .build();

    private Long getIdByName(String name) {
        return jdbcTemplate.queryForObject("SELECT FILM_ID FROM FILM WHERE name = ?", Long.class, name);
    }

}
