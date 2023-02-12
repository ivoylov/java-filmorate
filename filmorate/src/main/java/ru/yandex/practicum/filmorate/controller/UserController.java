package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.time.LocalDate;
import java.util.HashMap;

@RestController
public class UserController {

    HashMap<Integer,User> users = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    public User addUser(@Validated @RequestBody User user) {
        if (validateUser(user)) {
            return users.put(user.getId(), user);
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
    }

    public User updateUser(@RequestBody User user) {
        if (validateUser(user)) {
            return users.put(user.getId(), user);
        } else {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
    }

    public HashMap<Integer,User> getAllUsers() {
        return new HashMap<>(users);
    }

    public User getUserById(int id) {
        return users.get(id);
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
