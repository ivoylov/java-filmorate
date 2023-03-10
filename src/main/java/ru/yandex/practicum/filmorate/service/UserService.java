package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.user.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService (InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void add(User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        userStorage.create(user);
        logger.info(user + " добавлен");
    }

    public void update(User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        if (!userStorage.isExist(user.getId())) {
            logger.info(user + " отсутствует в списке");
            throw new UserUnknownException();
        }
        userStorage.update(user);
        logger.info(user + "обновлён");
    }

    public ArrayList<User> getAll() {
        return userStorage.findAll();
    }

    public User getById(Long id) {
        return userStorage.find(id);
    }

    public ArrayList<User> getAllFriend(long id) {
        HashSet<Long> friendsId = userStorage.find(id).getAllFriends();
        ArrayList<User> friends = new ArrayList<>();
        for (Long friendId : friendsId) {
            friends.add(userStorage.find(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(long id, long otherId) {
        ArrayList<User> userFriends = getAllFriend(id);
        ArrayList<User> otherUserFriends = getAllFriend(otherId);
        return userFriends.stream().filter(otherUserFriends::contains).collect(Collectors.toList());
    }

    public boolean isExist(long id) {
        return userStorage.isExist(id);
    }

    public boolean isValid(User user) {
        if (user.getLogin().contains(" ")) return false;
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return true;
    }

    public void deleteFriend(long id, long friendId) {
        userStorage.find(id).deleteFriend(friendId);
        userStorage.find(friendId).deleteFriend(id);
    }

    public void addFriend(long id, long friendId) {
        userStorage.find(id).addFriend(friendId);
        userStorage.find(friendId).addFriend(id);
    }

}
