package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserUnknownException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserStorage userStorage;

    @Autowired
    public UserService (UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void add(User user) {
        if (!isValid(user)) {
            logger.info(user + " не прошёл валидацию");
            throw new UserValidationException();
        }
        userStorage.add(user);
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
    }

    public ArrayList<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public void addFriend(User user, User friend) {
        user.addFriend(friend.getId());
    }

    public void deleteFriend(User user, User friend) {
        user.deleteFriend(friend.getId());
    }

    public List<User> getAllFriend(User user) {
        List<Long> friendsId = user.getAllFriends();
        ArrayList<User> friends = new ArrayList<>();
        for (Long friendId : friendsId) {
            friends.add(userStorage.getById(friendId));
        }
        return friends;
    }

    public List<Long> getCommonFriends(User user, User friend) {
        List<Long> userFriends = user.getAllFriends();
        List<Long> friendFriends = friend.getAllFriends();
        return userFriends.stream().filter(friendFriends::contains).collect(Collectors.toList());
    }

    public boolean isValid(User user) {
        if (user.getLogin().contains(" ")) return false;
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!user.isFriendsExist()) {
            user.setFriends();
        }
        return true;
    }

}
