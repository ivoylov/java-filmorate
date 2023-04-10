package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component
@AllArgsConstructor
public class InDbFilmStorage implements Storage<Film> {

    private final JdbcTemplate jdbcTemplate;
    private InDbMpaStorage inDbMpaStorage;
    private InDbGenreStorage inDbGenreStorage;
    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);

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
        film.setId(getIdByFilmDescriptions(film.getName(), film.getDescription()));
        refreshFilmGenres(film);
        return film;
    }

    private void refreshFilmGenres(Film film) {
        ArrayList<Genre> uniqueGenre = film.getGenres().stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        deleteFilmGenres(film.getId());
        createFilmGenres(film.getId(), uniqueGenre);
        film.setGenres(uniqueGenre);
    }

    private void createFilmGenres(Long filmId, List<Genre> genres) {
        for (Genre genre : genres) {
            String query = "INSERT INTO film_genre (film_id, genre_id) " +
                    "VALUES (?,?)";
            jdbcTemplate.update(query, filmId, genre.getId());
        }
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
        refreshFilmGenres(film);
        return film;
    }

    private void deleteFilmGenres(long filmId) {
        try {
            String deleteQuery = "DELETE FROM film_genre WHERE film_id = ?";
            jdbcTemplate.update(deleteQuery, filmId);
        } catch (Exception e) {
            logger.info("жанры фильма не обнаружены");
        }
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
            .genres(inDbGenreStorage.findAllFilmGenres(recordSet.getLong("film_id")))
            .build();

    private Long getIdByFilmDescriptions(String name, String description) {
        return jdbcTemplate.queryForObject(
                "SELECT FILM_ID " +
                "FROM FILM " +
                "WHERE (name = ? AND description = ?)",
                Long.class,
                name, description);
    }

    public void addLike(long filmId, long userId) {
        String query = "INSERT INTO likes " +
                "(film_id, user_id) " +
                "VALUES (?,?)";
        jdbcTemplate.update(query, filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        String query = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(query, filmId, userId);
    }

    public List<Film> getTopFilm(int top) {
        String likesQuery = "SELECT * FROM film " +
                "WHERE film_id IN " +
                "(SELECT film_id " +
                "FROM likes " +
                "GROUP BY film_id " +
                "ORDER BY SUM(user_id) " +
                "LIMIT ?)";
        List<Film> likes = jdbcTemplate.query(likesQuery, filmRowMapper, top);
        if (likes.size() == 0) {
            return jdbcTemplate.query("SELECT * FROM film LIMIT ?", filmRowMapper, top);
        }
        return likes;
    }

}
