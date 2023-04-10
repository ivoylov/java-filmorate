package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import java.util.ArrayList;

@RestController
@Validated
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public ArrayList<Mpa> getAll() {
        return mpaService.getAll();
    }

    @GetMapping("{id}")
    public Mpa getById(@PathVariable("id") Long id) {
        if (!mpaService.isExist(id)) {
            throw new UserUnknownException();
        }
        return mpaService.getById(id);
    }

}
