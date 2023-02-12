package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.time.LocalDate;
import java.util.HashSet;

@RestController
public class UserController {

    HashSet<User> users = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    public User addUser(@RequestBody User user) {
        if (validateUser(user)) {
            if (users.add(user)) {
                logger.info(user + " добавлен");
                return user;
            }
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        logger.info(user + " не добавлен");
        return null;
    }

    public User updateUser(@RequestBody User user) {
        if (validateUser(user)) {
            if (users.add(user)) {
                logger.info(user + " обновлён");
                return user;
            }
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new FilmValidationException();
        }
        logger.info(user + " не обновлён");
        return null;
    }

    public HashSet<User> getAllUsers() {
        return new HashSet<>(users);
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
