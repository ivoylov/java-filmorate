package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    void add(User user);
    void update(User user);
    boolean isExist (Long userId);
    ArrayList<User> getAll();
    User getById(Long id);

}
