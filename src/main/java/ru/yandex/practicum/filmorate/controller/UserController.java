package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.user.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
        userService.update(user);
        return user;
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
        return userService.getAllFriend(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        if (!userService.isExist(id) || !userService.isExist(friendId)) {
            throw new UserUnknownException();
        }
        userService.deleteFriend(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        if (!userService.isExist(id) || !userService.isExist(friendId)) {
            throw new UserUnknownException();
        }
        userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends (@PathVariable long id, @PathVariable long otherId) {
        if (!userService.isExist(id) || !userService.isExist(otherId)) {
            throw new UserUnknownException();
        }
        return new ArrayList<>(userService.getCommonFriends(id, otherId));
    }

}
