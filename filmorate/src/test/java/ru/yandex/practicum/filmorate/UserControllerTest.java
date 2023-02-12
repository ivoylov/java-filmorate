package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

public class UserControllerTest {

    UserController userController = new UserController();

    @Test
    void setLoginInNameIfBlankName() {
        userController.addUser(getUserWithBlankName());
        assertEquals(userController.getUserById(1).getLogin(), userController.getUserById(1).getName());
    }

    @Test
    void notValidateIncorrectEmail() {
        assertThrows(UserValidationException.class, () -> userController.addUser(getUserWithIncorrectEmail()));
    }

    @Test
    void notValidateBlankLogin() {
        assertThrows(UserValidationException.class, () -> userController.addUser(getUserWithBlankLogin()));
    }

    @Test
    void notValidateLoginContainsSpace() {
        assertThrows(UserValidationException.class, () -> userController.addUser(getUserWithLoginContainsSpace()));
    }

    @Test
    void notValidateIncorrectBirthdate() {
        assertThrows(UserValidationException.class, () -> userController.addUser(getUserWithIncorrectBirthdate()));
    }

    private User getUserWithBlankName() {
        return User.builder()
                .name("")
                .birthday(LocalDate.of(2000,1,1))
                .id(1)
                .email("practicum@yandex.ru")
                .login("yandex")
                .build();
    }

    private User getUserWithIncorrectEmail() {
        return User.builder()
                .name("Имя")
                .birthday(LocalDate.of(2000,1,1))
                .id(1)
                .email("yandex")
                .login("yandex")
                .build();
    }

    private User getUserWithBlankLogin() {
        return User.builder()
                .name("Имя")
                .birthday(LocalDate.of(2000,1,1))
                .id(1)
                .email("practicum@yandex.ru")
                .login("")
                .build();
    }

    private User getUserWithLoginContainsSpace() {
        return User.builder()
                .name("Имя")
                .birthday(LocalDate.of(2000,1,1))
                .id(1)
                .email("practicum@yandex.ru")
                .login("lo gin")
                .build();
    }

    private User getUserWithIncorrectBirthdate() {
        return User.builder()
                .name("Имя")
                .birthday(LocalDate.of(2030,1,1))
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .build();
    }

}
