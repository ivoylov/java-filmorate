package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    HashMap<Integer,User> users = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private int idCounter = 0;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        if (validateUser(user)) {
            user.setId(++idCounter);
            users.put(idCounter, user);
            logger.info(user + " добавлен.");
            return user;
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (validateUser(user) && users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            logger.info(user + " обновлён.");
            return user;
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") int id) {
        return users.get(id);
    }

    private boolean validateUser(User user) {
        if (user.getLogin().contains(" ")) return false;
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        return true;
    }

}
