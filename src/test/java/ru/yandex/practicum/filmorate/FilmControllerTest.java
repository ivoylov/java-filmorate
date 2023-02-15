package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

public class FilmControllerTest {

    @Test
    void notValidateIncorrectReleaseDate() {
        FilmController filmController = new FilmController();
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(getFilmWithIncorrectReleaseDate()));
    }

    @Test
    void getAllFilms() {
        FilmController filmController = new FilmController();
        Film film = getBaseFilm();
        filmController.addFilm(film);
        assertEquals(filmController.getAllFilms().get(0),film);
    }

    @Test
    void getUserById() {
        FilmController filmController = new FilmController();
        Film film = getBaseFilm();
        filmController.addFilm(film);
        assertEquals(filmController.getFilmById(1),film);
    }

    private Film getBaseFilm() {
        return Film.builder()
                .id(1)
                .name("Имя")
                .description("Описание")
                .duration(100L)
                .releaseDate(LocalDate.of(2023,2,12))
                .build();
    }

    private Film getFilmWithIncorrectReleaseDate() {
        LocalDate incorrectDate = Film.getMinimalReleaseDate().minusDays(1);
        Film film = getBaseFilm();
        film.setReleaseDate(incorrectDate);
        return film;
    }

}
