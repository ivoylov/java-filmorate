package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements Storage<User> {

    private final HashMap<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public User create(User user) {
        user.setId(++idCounter);
        users.put(idCounter, user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User find(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        return null;
    }

    @Override
    public ArrayList<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }

    @Override
    public boolean isExist(long id) {
        return users.containsKey(id);
    }

}
