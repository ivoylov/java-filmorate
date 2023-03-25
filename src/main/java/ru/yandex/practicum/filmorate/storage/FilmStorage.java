package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;

public interface FilmStorage extends Storage<Film> {
    List<Genre> getAllGenres();
}
