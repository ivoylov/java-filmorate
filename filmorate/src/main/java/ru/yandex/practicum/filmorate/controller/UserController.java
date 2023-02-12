package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.time.LocalDate;
import java.util.HashMap;

@RestController
public class UserController {

    HashMap<Integer,User> users = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    public User addUser(@RequestBody User user) {
        if (validateUser(user)) {
            return users.put(user.getId(), user);
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    public User updateUser(@RequestBody User user) {
        if (validateUser(user)) {
            return users.put(user.getId(), user);
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new FilmValidationException();
        }
    }

    public HashMap<Integer,User> getAllUsers() {
        return new HashMap<>(users);
    }

    private boolean validateUser(User user) {
        if (user.getEmail().isBlank() ||
            !user.getEmail().contains("@") ||
            user.getLogin().isBlank() ||
            user.getLogin().contains(" ") ||
            user.getBirthday().isAfter(LocalDate.now())
        ) return false;
        if (user.getName().isBlank()) user.setName(user.getLogin());
        return true;
    }

}
