package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.storage.film.InDbFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InDbUserStorage;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private static EmbeddedDatabase embeddedDatabase;
	private static JdbcTemplate jdbcTemplate;
	private static InDbUserStorage userStorage;
    private static InDbFilmStorage filmStorage;

	@BeforeAll
	public static void setUp() {
		embeddedDatabase = new EmbeddedDatabaseBuilder()
				.addDefaultScripts() // Добавляем скрипты schema.sql и data.sql
				.setType(EmbeddedDatabaseType.H2)
				.build();
		jdbcTemplate = new JdbcTemplate(embeddedDatabase);
		userStorage = new InDbUserStorage(jdbcTemplate);
        filmStorage = new InDbFilmStorage(jdbcTemplate);
        addUsersToDb();
        addFilmsToDb();
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

    @Test
    public void testFindFilmById() {
        Film film = filmStorage.find(1);
        assertThat(film).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    public void testUpdateFilm() {
        Film film = filmStorage.find(1);
        film.setName("updatedFilm");
        filmStorage.update(film);
        Film film1 = filmStorage.find(1);
        assertThat(film1).hasFieldOrPropertyWithValue("name", "updatedFilm");
    }

    @Test
    public void testFindAllFilms() {
        Assertions.assertEquals(filmStorage.findAll().size(), 2);
    }

    @Test
    public void testDeleteFilm() {
        filmStorage.delete(2);
        Assertions.assertEquals(filmStorage.findAll().size(), 1);
    }

    @Test
    public void testIsExistFilm() {
        Assertions.assertTrue(filmStorage.isExist(1));
    }

	@AfterAll
	public static void tearDown() {
		embeddedDatabase.shutdown();
	}

    private static void addUsersToDb () {
        User user1 = User.builder()
                .birthday(LocalDate.of(1988,8,16))
                .login("login")
                .name("name")
                .email("email@mail.ru")
                .build();
        userStorage.create(user1);
        User user2 = User.builder()
                .birthday(LocalDate.of(1988,8,16))
                .login("login")
                .name("anotherName")
                .email("email@mail.ru")
                .build();
        userStorage.create(user2);
    }

    private static void addFilmsToDb () {
        Film film1 = Film.builder()
                .name("name1")
                .description("description1")
                .duration(120L)
                .releaseDate(LocalDate.of(2000,1,1))
                .rating(Rating.R)
                .genre(Genre.ACTION)
                .build();
        filmStorage.create(film1);
        Film film2 = Film.builder()
                .name("name2")
                .description("description2")
                .duration(120L)
                .releaseDate(LocalDate.of(2000,1,1))
                .rating(Rating.G)
                .genre(Genre.CARTOON)
                .build();
        filmStorage.create(film2);
    }

}
