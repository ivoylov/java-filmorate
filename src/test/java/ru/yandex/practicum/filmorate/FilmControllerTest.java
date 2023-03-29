package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InDbFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InDbGenreStorage;
import ru.yandex.practicum.filmorate.storage.user.InDbUserStorage;
import java.time.LocalDate;

public class FilmControllerTest {

    @Test
    void notValidateIncorrectReleaseDate() {
        //FilmController filmController = new FilmController(
                //new FilmService(new InDbFilmStorage(new JdbcTemplate()), new InDbGenreStorage()),
                //new UserService(new InDbUserStorage(new JdbcTemplate())));
        // TODO assertThrows(FilmValidationException.class, () -> filmController.add(getFilmWithIncorrectReleaseDate()));
    }

    @Test
    void getAllFilms() {
        //FilmController filmController = new FilmController(
                //new FilmService(new InDbFilmStorage(new JdbcTemplate()), new InDbGenreStorage()),
                //new UserService(new InDbUserStorage(new JdbcTemplate())));
        Film film = getBaseFilm();
        // TODO filmController.add(film);
        //assertEquals(filmController.getAll().get(0),film);
    }

    @Test
    void getUserById() {
        //FilmController filmController = new FilmController(
                //new FilmService(new InDbFilmStorage(new JdbcTemplate()), new InDbGenreStorage()),
                //new UserService(new InDbUserStorage(new JdbcTemplate())));
        Film film = getBaseFilm();
        // TODO filmController.add(film);
        //assertEquals(filmController.getById(1),film);
    }

    private Film getBaseFilm() {
        return Film.builder()
                .id(1L)
                .name("Имя")
                .description("Описание")
                .duration(100L)
                .releaseDate(LocalDate.of(2023,2,12))
                .build();
    }

    private Film getFilmWithIncorrectReleaseDate() {
        LocalDate incorrectDate = Film.MINIMAL_RELEASE_DATE.minusDays(1);
        Film film = getBaseFilm();
        film.setReleaseDate(incorrectDate);
        return film;
    }

}
