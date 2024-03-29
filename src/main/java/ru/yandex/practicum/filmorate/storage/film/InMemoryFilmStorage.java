package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import java.util.ArrayList;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.storage.Storage;

@Component
public class InMemoryFilmStorage implements Storage<Film> {

    private final HashMap<Long, Film> films = new HashMap<>();
    private long idCounter = 0;

    @Override
    public Film create(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
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
