package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.user.UserValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.InDbUserStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final InDbUserStorage userStorage;

    @Autowired
    public UserService(InDbUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        logger.info(user + " добавлен");
        return userStorage.create(user);
    }

    public User update(User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        if (!userStorage.isExist(user.getId())) {
            logger.info(user + " отсутствует в списке");
            throw new UserUnknownException();
        }
        logger.info(user + "обновлён");
        return userStorage.update(user);
    }

    public ArrayList<User> getAll() {
        return userStorage.findAll();
    }

    public User getById(Long id) {
        return userStorage.find(id);
    }

    public ArrayList<User> getAllFriends(long userId) {
        ArrayList<Long> friendsId = userStorage.findFriends(userId);
        ArrayList<User> friends = new ArrayList<>();
        for (Long friendId : friendsId) {
            friends.add(userStorage.find(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(long id, long otherId) {
        ArrayList<User> userFriends = getAllFriends(id);
        ArrayList<User> otherUserFriends = getAllFriends(otherId);
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

    public void deleteFriend(long userId, long friendId) {
        if (!userStorage.isExist(userId) || !userStorage.isExist(friendId)) {
            throw new UserUnknownException();
        }
        userStorage.deleteRelationship(userId, friendId);
    }

    public void addFriend(long userId, long friendId) {
        userStorage.createRelationship(userId, friendId);
    }

}
