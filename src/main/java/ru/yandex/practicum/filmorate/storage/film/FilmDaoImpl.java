package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Rating;
import java.util.ArrayList;

public class FilmDaoImpl implements FilmDao {

    private final Logger log = LoggerFactory.getLogger(FilmDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Film film) {

    }

    @Override
    public void update(Film film) {

    }

    @Override
    public Film find(long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM film WHERE film_id = ?", id);
        if(filmRows.next()) {
            Film film = Film.builder()
                    .id(filmRows.getLong("film_id"))
                    .name(filmRows.getString("name"))
                    .description(filmRows.getString("description"))
                    .genre(Genre.valueOf(filmRows.getString("genre").toUpperCase()))
                    .rating(Rating.valueOf(filmRows.getString("rating").toUpperCase()))
                    .duration(filmRows.getLong("duration"))
                    .releaseDate(filmRows.getDate("release_date").toLocalDate())
                    .build();
            log.info("Найден пользователь: {} {}", film.getId(), film.getName());
            return film;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public ArrayList<Film> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

}
