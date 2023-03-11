package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.storage.Storage;

@Component
public class InMemoryFilmStorage implements Storage<Film> {

    private final HashMap<Long, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private long idCounter = 0;

    @Override
    public void create(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
        logger.info(film + " добавлен.");
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
    public void delete(long id) {
        films.remove(id);
    }

    @Override
    public void update(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public boolean isExist(long id) {
        return films.containsKey(id);
    }

}
