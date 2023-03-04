package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    void add(User user);
    void update(User user);
    boolean isExist (int userId);
    ArrayList<User> getAll();
    public User getById(int id);

}
