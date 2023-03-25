package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmUnknownException;
import ru.yandex.practicum.filmorate.exception.film.FilmValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmDaoImpl;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final FilmDaoImpl filmStorage;

    @Autowired
    public FilmService(FilmDaoImpl filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void add(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        filmStorage.create(film);
        logger.info(film + " добавлен");
    }

    public void update(Film film) {
        if (!isValid(film)) {
            logger.info(film + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        if (!filmStorage.isExist(film.getId())) {
            logger.info(film + " отсутствует в списке");
            throw new FilmUnknownException();
        }
        filmStorage.update(film);
        logger.info(film + " обновлён");
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
        return filmStorage.getAllGenres();
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
