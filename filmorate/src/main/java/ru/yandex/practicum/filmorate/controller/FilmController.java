package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;

@RestController
public class FilmController {

    HashSet<Film> films = new HashSet<>();

    public void addFilm(Film film) {
        films.add(film);
    }

    public void updateFilm(Film film) {
        films.add(film);
    }

    public HashSet<Film> getAllFilms() {
        return new HashSet<>(films);
    }

}
