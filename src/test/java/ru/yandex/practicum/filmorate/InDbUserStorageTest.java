package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.InDbUserStorage;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InDbUserStorageTest {

    private static EmbeddedDatabase embeddedDatabase;
    private static JdbcTemplate jdbcTemplate;
    private static InDbUserStorage userStorage;

    @BeforeAll
    public static void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        userStorage = new InDbUserStorage(jdbcTemplate);
        addUsersToDb();
    }

    @Test
    public void testFindUserById() {
        User user = userStorage.find(1);
        assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    public void testUpdateUser() {
        User user = userStorage.find(1);
        user.setName("updatedName");
        userStorage.update(user);
        User user1 = userStorage.find(1);
        assertThat(user1).hasFieldOrPropertyWithValue("name", "updatedName");
    }

    @Test
    public void testFindAllUsers() {
        Assertions.assertEquals(userStorage.findAll().size(), 2);
    }

    @Test
    public void testDeleteUser() {
        userStorage.delete(2);
        Assertions.assertEquals(userStorage.findAll().size(), 1);
    }

    @Test
    public void testIsExistUser() {
        Assertions.assertTrue(userStorage.isExist(1));
    }

    @AfterAll
    public static void tearDown() {
        embeddedDatabase.shutdown();
    }

    private static void addUsersToDb() {
        User user1 = User.builder()
                .birthday(LocalDate.of(1988,8,16))
                .login("login")
                .name("name")
                .email("email@mail.ru")
                .build();
        userStorage.create(user1);
        User user2 = User.builder()
                .birthday(LocalDate.of(1988,8,16))
                .login("anotherLogin")
                .name("anotherName")
                .email("email@mail.ru")
                .build();
        userStorage.create(user2);
    }

}
