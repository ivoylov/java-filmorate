package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;

@RestController
public class FilmController {

    HashSet<Film> films = new HashSet<>();

    public Film addFilm(Film film) {
        if (films.add(film)) return film;
        return null;
    }

    public Film updateFilm(Film film) {
        if (films.add(film)) return film;
        return null;
    }

    public HashSet<Film> getAllFilms() {
        return new HashSet<>(films);
    }

}
