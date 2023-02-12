package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.HashSet;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashSet<Film> films = new HashSet<>();
    private final int MAX_LENGTH_FILM_DESCRIPTION = 200;
    private final LocalDate MINIMAL_RELEASE_DATE = LocalDate.of(1895, 112, 28);
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            if (films.add(film)) {
                logger.info(film + " добавлен");
                return film;
            }
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        logger.info(film + " не добавлен");
        return null;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            if (films.add(film)) {
                logger.info(film + " обновлён");
                return film;
            }
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        logger.info(film + " не обновлён");
        return null;
    }

    @GetMapping
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
