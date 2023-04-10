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
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.film.InDbFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InDbGenreStorage;
import ru.yandex.practicum.filmorate.storage.film.InDbMpaStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InDbFilmStorageTest {

    private static EmbeddedDatabase embeddedDatabase;
    private static JdbcTemplate jdbcTemplate;
    private static InDbFilmStorage filmStorage;
    private static InDbMpaStorage inDbMpaStorage;
    private static InDbGenreStorage inDbGenreStorage;

    @BeforeAll
    public static void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        inDbMpaStorage = new InDbMpaStorage(jdbcTemplate);
        inDbGenreStorage = new InDbGenreStorage(jdbcTemplate);
        filmStorage = new InDbFilmStorage(jdbcTemplate, inDbMpaStorage, inDbGenreStorage);
        addFilmsToDb();
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

    private static void addFilmsToDb() {
        Film film1 = Film.builder()
                .id(1L)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1999,1,1))
                .duration(120L)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new ArrayList<>())
                .build();
        filmStorage.create(film1);
        Film film2 = Film.builder()
                .id(1L)
                .name("another name")
                .description("another description")
                .releaseDate(LocalDate.of(1999,1,2))
                .duration(120L)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new ArrayList<>())
                .build();
        filmStorage.create(film2);
    }

}
