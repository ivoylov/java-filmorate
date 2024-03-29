package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.film.InDbMpaStorage;
import java.util.ArrayList;

@Service
public class MpaService {

    private final InDbMpaStorage mpaStorage;

    @Autowired
    public MpaService(InDbMpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public ArrayList<Mpa> getAll() {
        return mpaStorage.findAll();
    }

    public boolean isExist(Long id) {
        return mpaStorage.isExist(id);
    }

    public Mpa getById(Long id) {
        return mpaStorage.find(id);
    }

}
