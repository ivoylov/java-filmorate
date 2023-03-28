package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmUnknownException;
import ru.yandex.practicum.filmorate.exception.film.FilmValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.InDbFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InDbGenreStorage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final InDbFilmStorage filmStorage;
    private final InDbGenreStorage genreStorage;

    @Autowired
    public FilmService(InDbFilmStorage filmStorage, InDbGenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.genreStorage = genreStorage;
    }

    public Film add(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
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
        logger.info(film + " обновлён");
        return filmStorage.update(film);
    }

    public ArrayList<Film> getAll() {
        return new ArrayList<>(filmStorage.findAll());
    }

    public Film getById(long id) {
        return filmStorage.find(id);
    }

    public void addLike(long filmId, long userId) {
        filmStorage.find(filmId).addLike(userId);
    }

    public void deleteLike(long filmId, long userId) {
        filmStorage.find(filmId).deleteLike(userId);
    }

    public List<Film> getTopFilms(long top) {
        return sortedFilmsByLikesQuantity()
                .stream()
                .limit(top)
                .collect(Collectors.toList());
    }

    public List<Genre> getAllGenres() {
        return genreStorage.findAll();
    }

    public boolean isValid(Film film) {
        return !film.getReleaseDate().isBefore(Film.MINIMAL_RELEASE_DATE);
    }

    public boolean isExist(long id) {
        return filmStorage.isExist(id);
    }

    private List<Film> sortedFilmsByLikesQuantity() {
        return filmStorage.findAll()
                .stream()
                .sorted(Comparator.comparingLong(f0 -> f0.getLikes().size()*-1))
                .collect(Collectors.toList());
    }

}
