package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        /*
        Integer id = rs.getInt("id");
        String description = rs.getString("description");
        String photoUrl = rs.getString("photo_url");
        LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
        return new Post(id, user, description, photoUrl, creationDate);
        */
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User find(long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM filmorate_user WHERE filmorate_user_id = ?", id);
        if(userRows.next()) {
            User user = User.builder()
                    .id(userRows.getLong("filmorate_user_id"))
                    .login(userRows.getString("login"))
                    .name(userRows.getString("name"))
                    .email(userRows.getString("email"))
                    .birthday(userRows.getDate("birthdate").toLocalDate())
                    .build();
            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());
            return user;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean isExist(long id) {
        return false;
    }
}
