package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

public class FilmControllerTest {

    @Test
    void notValidateIncorrectReleaseDate() {
        FilmController filmController = new FilmController(
                new FilmService(new InMemoryFilmStorage()),
                new UserService(new InMemoryUserStorage()));
        assertThrows(FilmValidationException.class, () -> filmController.add(getFilmWithIncorrectReleaseDate()));
    }

    @Test
    void getAllFilms() {
        FilmController filmController = new FilmController(
                new FilmService(new InMemoryFilmStorage()),
                new UserService(new InMemoryUserStorage()));
        Film film = getBaseFilm();
        filmController.add(film);
        assertEquals(filmController.getAll().get(0),film);
    }

    @Test
    void getUserById() {
        FilmController filmController = new FilmController(
                new FilmService(new InMemoryFilmStorage()),
                new UserService(new InMemoryUserStorage()));
        Film film = getBaseFilm();
        filmController.add(film);
        assertEquals(filmController.getById(1),film);
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
