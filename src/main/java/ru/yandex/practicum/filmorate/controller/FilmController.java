package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private int idCounter = 0;

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        film.setId(++idCounter);
        films.put(idCounter, film);
        logger.info(film + " добавлен.");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        if (!films.containsKey(film.getId())) {
            logger.info(film + " отсутствует в списке");
            throw new FilmValidationException();
        }
        films.put(film.getId(), film);
        logger.info(film + " обновлён.");
        return film;
    }

    @GetMapping
    public ArrayList<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @GetMapping("{id}")
    public Film getById(@PathVariable("id") int id) {
        return films.get(id);
    }

    private boolean isValid(Film film) {
        return !film.getReleaseDate().isBefore(Film.getMinimalReleaseDate());
    }

}
