package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer,Film> films = new HashMap<>();
    private final int MAX_LENGTH_FILM_DESCRIPTION = 200;
    private final LocalDate MINIMAL_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            return films.put(film.getId(), film);
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            return films.put(film.getId(), film);
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    @GetMapping
    public HashMap<Integer,Film> getAllFilms() {
        return new HashMap<>(films);
    }

    private boolean validateFilm(Film film) {
        if (film.getName().isBlank() ||
            film.getDescription().length() > MAX_LENGTH_FILM_DESCRIPTION ||
            film.getReleaseDate().isBefore(MINIMAL_RELEASE_DATE) ||
            film.getDuration().isZero()) return false;
        return true;
    }

    public int getMaxLengthFilmDescription() {
        return MAX_LENGTH_FILM_DESCRIPTION;
    }

    public LocalDate getMinimalReleaseDate() {
        return MINIMAL_RELEASE_DATE;
    }
}
