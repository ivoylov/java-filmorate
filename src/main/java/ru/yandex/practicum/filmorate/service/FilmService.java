package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.ArrayList;

@Service
public class FilmService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void add(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        filmStorage.add(film);
    }

    public void update(@Valid @RequestBody Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        if (!filmStorage.isExist(film.getId())) {
            logger.info(film + " отсутствует в списке");
            throw new FilmValidationException();
        }
        filmStorage.update(film);
        logger.info(film + " обновлён.");
    }

    public ArrayList<Film> getAll() {
        return new ArrayList<>(filmStorage.getAll());
    }

    public Film getById(int id) {
        return filmStorage.getById(id);
    }

    public boolean isValid(Film film) {
        return !film.getReleaseDate().isBefore(Film.MINIMAL_RELEASE_DATE);
    }

}
