package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private long idCounter = 0;

    @Override
    public Film create(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
        logger.info(film + " добавлен.");
        return film;
    }

    @Override
    public Film find(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        return null;
    }

    @Override
    public ArrayList<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Genre> getAllGenres() {
        return new ArrayList<>(List.of(Genre.values()));
    }

    @Override
    public void delete(long id) {
        films.remove(id);
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public boolean isExist(long id) {
        return films.containsKey(id);
    }

}
