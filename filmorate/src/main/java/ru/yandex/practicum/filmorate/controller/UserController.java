package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;

@RestController
public class UserController {

    HashSet<User> users = new HashSet<>();

    public User addUser(User user) {
        if (users.add(user)) return user;
        return null;
    }

    public User updateUser(User user) {
        if (users.add(user)) return user;
        return null;
    }

    public HashSet<User> getAllUsers() {
        return new HashSet<>(users);
    }

}
