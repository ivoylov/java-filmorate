package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import java.util.ArrayList;

@RestController
@Validated
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ArrayList<Genre> getAllGenres() {
        return new ArrayList<>(genreService.getAll());
    }

    @GetMapping("{id}")
    public Genre getById(@PathVariable("id") Long id) {
        if (!genreService.isExist(id)) {
            throw new UserUnknownException();
        }
        return genreService.getById(id);
    }

}
