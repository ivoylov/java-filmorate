package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private final HashMap<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public void add(User user) {
        user.setId(++idCounter);
        users.put(idCounter, user);
        logger.info(user + " добавлен.");
    }

    @Override
    public void update(User user) {
        users.put(user.getId(),user);
    }

    @Override
    public boolean isExist(Long userId) {
        return users.containsKey(userId);
    }

    public ArrayList<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getById(Long id) {
        return users.get(id);
    }

}
