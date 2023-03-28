package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class InDbGenreStorage implements Storage<Genre> {

    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public Genre find(long id) {
        return null;
    }

    @Override
    public ArrayList<Genre> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean isExist(long id) {
        return false;
    }
}
