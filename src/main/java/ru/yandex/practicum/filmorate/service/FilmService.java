package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmUnknownException;
import ru.yandex.practicum.filmorate.exception.film.FilmValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.storage.film.InDbFilmStorage;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final InDbFilmStorage filmStorage;

    @Autowired
    public FilmService(InDbFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film add(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        }
        logger.info(film + " добавлен");
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        if (!filmStorage.isExist(film.getId())) {
            logger.info(film + " отсутствует в списке");
            throw new FilmUnknownException();
        }
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        }
        logger.info(film + " обновлён");
        return filmStorage.update(film);
    }

    public ArrayList<Film> getAll() {
        return new ArrayList<>(filmStorage.findAll());
    }

    public Film getById(long id) {
        if (!filmStorage.isExist(id)) {
            logger.info("film c id " + id + " отсутствует в списке");
            throw new FilmUnknownException();
        }
        return filmStorage.find(id);
    }

    public void addLike(long filmId, long userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getTopFilms(int top) {
        return filmStorage.getTopFilm(top);
    }

    public boolean isValid(Film film) {
        return !film.getReleaseDate().isBefore(Film.MINIMAL_RELEASE_DATE);
    }

    public boolean isExist(long id) {
        return filmStorage.isExist(id);
    }


}
