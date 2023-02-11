package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.HashSet;

@RestController
public class FilmController {

    HashSet<Film> films = new HashSet<>();

    public Film addFilm(@RequestBody Film film) {
        if (films.add(film)) return film;
        return null;
    }

    public Film updateFilm(@RequestBody Film film) {
        if (films.add(film)) return film;
        return null;
    }

    public HashSet<Film> getAllFilms() {
        return new HashSet<>(films);
    }

    private boolean validateFilm(Film film) {
        if (film.getName().isBlank() ||
            film.getDescription().length() > 200 ||
            film.getReleaseDate().isBefore(LocalDate.of(1895, 112, 28)) ||
            film.getDuration().isZero()) return false;
        return true;
    }

}
