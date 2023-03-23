package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
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
import ru.yandex.practicum.filmorate.storage.user.UserDaoImpl;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private static EmbeddedDatabase embeddedDatabase;
	private static JdbcTemplate jdbcTemplate;
	private static UserDaoImpl userStorage;

	@BeforeAll
	public static void setUp() {
		embeddedDatabase = new EmbeddedDatabaseBuilder()
				.addDefaultScripts() // Добавляем скрипты schema.sql и data.sql
				.setType(EmbeddedDatabaseType.H2)
				.build();
		jdbcTemplate = new JdbcTemplate(embeddedDatabase);
		userStorage = new UserDaoImpl(jdbcTemplate);
	}

	@Test
	public void testCreateUserWithId() {
		User user = User.builder()
				.birthday(LocalDate.of(1988,8,16))
				.login("login")
				.name("name")
				.email("email@mail.ru")
				.build();
		userStorage.create(user);
	}

	@Test
	public void testFindUserById() {
		User user = userStorage.find(1);
		assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
	}

	@AfterAll
	public static void tearDown() {
		embeddedDatabase.shutdown();
	}

}
