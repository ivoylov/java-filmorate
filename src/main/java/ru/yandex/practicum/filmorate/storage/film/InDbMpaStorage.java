package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class InDbMpaStorage implements Storage<Mpa> {
    @Override
    public Mpa create(Mpa mpa) {
        return null;
    }

    @Override
    public Mpa update(Mpa mpa) {
        return null;
    }

    @Override
    public Mpa find(long id) {
        return null;
    }

    @Override
    public ArrayList<Mpa> findAll() {
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
