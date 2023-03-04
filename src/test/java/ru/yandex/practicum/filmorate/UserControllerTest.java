package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

public class UserControllerTest {

    @Test
    void setLoginInNameIfBlankName() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        userController.add(getUserWithBlankName());
        assertEquals(userController.getById(1).getLogin(), userController.getById(1).getName());
    }

    @Test
    void setLoginInNameIfNullName() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        userController.add(getUserWithNullName());
        assertEquals(userController.getById(1).getLogin(), userController.getById(1).getName());
    }

    @Test
    void getAllUsers() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        User user = getBaseUser();
        userController.add(user);
        assertEquals(userController.getAll().get(0),user);
    }

    @Test
    void getUserById() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        User user = getBaseUser();
        userController.add(user);
        assertEquals(userController.getById(1),user);
    }

    @Test
    void notValidateLoginContainsSpace() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        assertThrows(UserValidationException.class, () -> userController.add(getUserWithLoginContainsSpace()));
    }

    private User getUserWithBlankName() {
        User user = getBaseUser();
        user.setName(" ");
        return user;
    }

    private User getUserWithNullName() {
        User user = getBaseUser();
        user.setName(null);
        return user;
    }

    private User getUserWithLoginContainsSpace() {
        User user = getBaseUser();
        user.setLogin("lo gin");
        return user;
    }

    private User getBaseUser() {
        return User.builder()
                .name("Имя")
                .birthday(LocalDate.of(2000,1,1))
                .email("practicum@yandex.ru")
                .login("login")
                .build();
    }

}