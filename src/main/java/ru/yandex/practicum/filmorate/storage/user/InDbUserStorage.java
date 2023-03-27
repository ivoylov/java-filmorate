package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class InDbUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        String sqlQuery = "INSERT INTO filmorate_user " +
                "(" +
                "login, " +
                "name, " +
                "email, " +
                "birthdate) " +
                "VALUES (?,?,?,?)";
        jdbcTemplate.update(sqlQuery,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday());
        user.setId(getIdByLogin(user.getLogin()));
        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE FILMORATE_USER SET " +
                "login = ?, " +
                "name = ?, " +
                "email = ?, " +
                "birthdate = ? " +
                "WHERE filmorate_user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public User find(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM filmorate_user WHERE filmorate_user_id = ?", userRowMapper, id);
    }

    @Override
    public ArrayList<User> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM FILMORATE_USER", userRowMapper));
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM FILMORATE_USER WHERE FILMORATE_USER_ID = ?", id);
    }

    @Override
    public boolean isExist(long id) {
        try {
            jdbcTemplate.queryForObject("SELECT filmorate_user_id FROM FILMORATE_USER WHERE FILMORATE_USER_ID = ?", Long.class, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final RowMapper<User> userRowMapper = (recordSet, rowNumber) -> User.builder()
            .id(recordSet.getLong("filmorate_user_id"))
            .login(recordSet.getString("login"))
            .name(recordSet.getString("name"))
            .email(recordSet.getString("email"))
            .birthday(recordSet.getDate("birthdate").toLocalDate())
            .build();

    private Long getIdByLogin(String login) {
        return jdbcTemplate.queryForObject("SELECT FILMORATE_USER_ID FROM filmorate_user WHERE login = ?", Long.class, login);
    }

}
