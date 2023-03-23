package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDaoImpl;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserDaoImpl userStorage = new UserDaoImpl();

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
		assertThat(user).hasFieldOrPropertyWithValue("id", 1);
	}

}
