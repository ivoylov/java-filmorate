package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    private int idCounter = 0;

    @Override
    public void add(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
        logger.info(film + " добавлен.");
    }

    @Override
    public void update(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isExist(int filmId) {
        return films.containsKey(filmId);
    }

    @Override
    public ArrayList<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(int id) {
        return films.get(id);
    }
}
