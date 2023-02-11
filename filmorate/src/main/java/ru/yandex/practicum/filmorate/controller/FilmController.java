package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.HashSet;

@RestController
public class FilmController {

    private final HashSet<Film> films = new HashSet<>();
    private final int MAX_LENGTH_FILM_DESCRIPTION = 200;
    private final LocalDate MINIMAL_RELEASE_DATE = LocalDate.of(1895, 112, 28);

    public Film addFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            if (films.add(film)) return film;
        } else {
            throw new FilmValidationException();
        }
        return null;
    }

    public Film updateFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            if (films.add(film)) return film;
        } else {
            throw new FilmValidationException();
        }
        return null;
    }

    public HashSet<Film> getAllFilms() {
        return new HashSet<>(films);
    }

    private boolean validateFilm(Film film) {
        if (film.getName().isBlank() ||
            film.getDescription().length() > MAX_LENGTH_FILM_DESCRIPTION ||
            film.getReleaseDate().isBefore(MINIMAL_RELEASE_DATE) ||
            film.getDuration().isZero()) return false;
        return true;
    }

}
