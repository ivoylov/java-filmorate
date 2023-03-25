package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class FilmDaoImpl implements FilmStorage {

    private final Logger log = LoggerFactory.getLogger(FilmDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Film film) {
        String sqlQuery = "insert into film" +
                "(name, " +
                "description, " +
                "release_date, " +
                "duration, " +
                "genre, " +
                "rating) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre().ordinal(),
                film.getRating().ordinal());
        log.info("В базу добавлен " + film);
    }

    @Override
    public void update(Film film) {
        String sqlQuery = "UPDATE film SET " +
                "name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ?, " +
                "genre = ?, " +
                "rating = ?" +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre().ordinal(),
                film.getRating().ordinal(),
                film.getId());
        log.info("В базе обновлён " + film);
    }

    @Override
    public Film find(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM film WHERE film_id = ?", filmRowMapper, id);
    }

    @Override
    public ArrayList<Film> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM film", filmRowMapper));
    }

    public List<Genre> getAllGenres() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM genre", genreRowMapper));
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM film WHERE film_id = ?", id);
        log.info("Из базы удалён фильм " + id);
    }

    @Override
    public boolean isExist(long id) {
        Long findId = jdbcTemplate.queryForObject("SELECT film_id FROM film WHERE film_id = ?", Long.class, id);
        return findId != null;
    }

    private final RowMapper<Film> filmRowMapper = (recordSet, rowNumber) -> Film.builder()
            .id(recordSet.getLong("film_id"))
            .name(recordSet.getString("name"))
            .description(recordSet.getString("description"))
            .releaseDate(recordSet.getDate("release_date").toLocalDate())
            .duration(recordSet.getLong("duration"))
            .genre(Genre.getGenre(recordSet.getInt("genre")))
            .rating(Rating.getRating(recordSet.getInt("rating")))
            .build();

    private final RowMapper<Genre> genreRowMapper = (recordSet, rowNumber) ->
            Genre.getGenre(recordSet.getInt("genre_id"));

}
