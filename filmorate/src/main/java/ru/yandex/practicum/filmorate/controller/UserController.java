package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;

@RestController
public class UserController {

    HashSet<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(User user) {
        users.add(user);
    }

    public HashSet<User> getAllUsers() {
        return new HashSet<>(users);
    }

}
