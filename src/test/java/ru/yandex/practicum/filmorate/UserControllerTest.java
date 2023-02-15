package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

public class UserControllerTest {

    @Test
    void setLoginInNameIfBlankName() {
        UserController userController = new UserController();
        userController.addUser(getUserWithBlankName());
        assertEquals(userController.getUserById(1).getLogin(), userController.getUserById(1).getName());
    }

    @Test
    void setLoginInNameIfNullName() {
        UserController userController = new UserController();
        userController.addUser(getUserWithNullName());
        assertEquals(userController.getUserById(1).getLogin(), userController.getUserById(1).getName());
    }

    @Test
    void getAllUsers() {
        UserController userController = new UserController();
        User user = getBaseUser();
        userController.addUser(user);
        assertEquals(userController.getAllUsers().get(0),user);
    }

    @Test
    void getUserById() {
        UserController userController = new UserController();
        User user = getBaseUser();
        userController.addUser(user);
        assertEquals(userController.getUserById(1),user);
    }

    @Test
    void notValidateLoginContainsSpace() {
        UserController userController = new UserController();
        assertThrows(UserValidationException.class, () -> userController.addUser(getUserWithLoginContainsSpace()));
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