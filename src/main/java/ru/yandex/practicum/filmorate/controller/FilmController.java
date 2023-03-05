package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController (FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        filmService.add(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        filmService.update(film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getAll() {
        return new ArrayList<>(filmService.getAll());
    }

    @GetMapping("{id}")
    public Film getById(@PathVariable("id") int id) {
        return filmService.getById(id);
    }

    /*
    PUT /films/{id}/like/{userId}  — пользователь ставит лайк фильму.
DELETE /films/{id}/like/{userId}  — пользователь удаляет лайк.
GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано, верните первые 10.
     */


}
