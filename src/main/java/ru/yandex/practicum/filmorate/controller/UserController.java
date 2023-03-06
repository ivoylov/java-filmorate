package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        if (!userService.isValid(user)) throw new UserValidationException();
        userService.add(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userService.update(user);
        return user;
    }

    @GetMapping
    public ArrayList<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) throw new UserUnknownException();
        return user;
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        return userService.getAllFriend(userService.getById(id));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.getById(id).deleteFriend(friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.getById(id).addFriend(friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<Long> getCommonFriends (@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(userService.getById(id), userService.getById(otherId));
    }

}
