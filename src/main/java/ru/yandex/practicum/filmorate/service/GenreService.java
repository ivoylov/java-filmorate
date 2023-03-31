package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.InDbGenreStorage;
import java.util.ArrayList;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final InDbGenreStorage inDbGenreStorage;

    @Autowired
    public GenreService(InDbGenreStorage inDbGenreStorage) {
        this.inDbGenreStorage = inDbGenreStorage;
    }

    public ArrayList<Genre> getAll() {
        return inDbGenreStorage.findAll();
    }

    public boolean isExist(Long id) {
        return inDbGenreStorage.isExist(id);
    }

    public Genre getById(Long id) {
        return inDbGenreStorage.find(id);
    }

}
