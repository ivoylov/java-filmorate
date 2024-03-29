package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.user.UserValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        if (!userService.isValid(user)) {
            throw new UserValidationException();
        }
        return userService.add(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!userService.isExist(user.getId())) {
            throw new UserUnknownException();
        }
        return userService.update(user);
    }

    @GetMapping
    public ArrayList<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Long id) {
        if (!userService.isExist(id)) {
            throw new UserUnknownException();
        }
        return userService.getById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        if (!userService.isExist(id)) {
            throw new UserUnknownException();
        }
        return userService.getAllFriends(id);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable long userId, @PathVariable long friendId) {
        if (!userService.isExist(userId) || !userService.isExist(friendId)) {
            throw new UserUnknownException();
        }
        userService.deleteFriend(userId, friendId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable long userId, @PathVariable long friendId) {
        if (!userService.isExist(userId) || !userService.isExist(friendId)) {
            throw new UserUnknownException();
        }
        userService.addFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        if (!userService.isExist(id) || !userService.isExist(otherId)) {
            throw new UserUnknownException();
        }
        return new ArrayList<>(userService.getCommonFriends(id, otherId));
    }

}
