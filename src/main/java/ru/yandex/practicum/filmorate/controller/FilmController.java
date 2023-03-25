package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.film.FilmUnknownException;
import ru.yandex.practicum.filmorate.exception.film.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.model.film.Film;
import java.util.ArrayList;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmController (FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        if (!filmService.isValid(film)) {
            throw new FilmValidationException();
        }
        filmService.add(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (!filmService.isExist(film.getId())) {
            throw new FilmUnknownException();
        }
        filmService.update(film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getAll() {
        return new ArrayList<>(filmService.getAll());
    }

    @GetMapping("{id}")
    public Film getById(@PathVariable int id) {
        if (!filmService.isExist(id)) {
            throw new FilmUnknownException();
        }
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        if (!filmService.isExist(id)) {
            throw new FilmUnknownException();
        }
        if (!userService.isExist(userId)) {
            throw new UserUnknownException();
        }
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        if (!filmService.isExist(id)) {
            throw new FilmUnknownException();
        }
        if (!userService.isExist(userId)) {
            throw new UserUnknownException();
        }
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getPopularFilm(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
        return new ArrayList<>(filmService.getTopFilms(count));
    }

    @GetMapping("/genres")
    public ArrayList<Genre> getAllGenres() {
        return new ArrayList<>(filmService.getAllGenres());
    }

}
