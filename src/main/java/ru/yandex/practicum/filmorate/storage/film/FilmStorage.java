package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    void add(Film film);
    void update(Film film);
    boolean isExist(long filmId);
    ArrayList<Film> getAll();
    Film getById(long id);

}
