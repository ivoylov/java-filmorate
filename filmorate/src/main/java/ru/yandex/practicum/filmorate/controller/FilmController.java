package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private int idCounter = 0;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (validateFilm(film)) {
            film.setId(++idCounter);
            films.put(idCounter, film);
            logger.info(film + " добавлен.");
            return film;
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (validateFilm(film) && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            logger.info(film + " обновлён.");
            return film;
        } else {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable("id") int id) {
        return films.get(id);
    }

    private boolean validateFilm(Film film) {
        return !film.getReleaseDate().isBefore(Film.getMinimalReleaseDate());
    }

}
