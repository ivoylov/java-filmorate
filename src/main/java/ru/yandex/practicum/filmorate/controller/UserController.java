package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
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

    private final HashMap<Integer,User> users = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private int idCounter = 0;

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        if (!isValid(user)) {
        logger.info(user + " не прошёл валидацию");
        throw new UserValidationException();
        }
        user.setId(++idCounter);
        users.put(idCounter, user);
        logger.info(user + " добавлен.");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        if (!users.containsKey(user.getId())) {
            logger.info(user + " отсутствует в списке");
            throw new UserValidationException();
        }
        users.put(user.getId(), user);
        logger.info(user + " обновлён.");
        return user;
    }

    @GetMapping
    public ArrayList<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") int id) {
        return users.get(id);
    }

    private boolean isValid(User user) {
        if (user.getLogin().contains(" ")) return false;
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return true;
    }

}
