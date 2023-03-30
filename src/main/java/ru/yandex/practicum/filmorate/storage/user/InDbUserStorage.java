package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.Relationship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.ArrayList;
import static ru.yandex.practicum.filmorate.model.user.Relationship.*;

@Component
@AllArgsConstructor
public class InDbUserStorage implements Storage<User> {

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

    private Relationship getCurrentRelationship(long lowerId) {
        Integer currentStatusId;
        try {
            currentStatusId = jdbcTemplate.queryForObject("SELECT status_id FROM relationship WHERE lower_id = ?", Integer.class, lowerId);
        } catch (Exception e) {
            return UNCONFIRMED;
        }
        if (currentStatusId == null) return UNCONFIRMED;
        return Relationship.getRelationship(currentStatusId);
    }

    public void createRelationship(long user, long friend) {
        // выбрать наименьший id пользователя для вставки в базу
        // выбрать пользователя, который запрашивает дружбу
        // проверить текущий статус
        // если статус - не дружат, сделать не подтверждён вторым пользователем
        // если статус уже подтверждён вторым пользователем, то обновить до статуса дружат
        long lowerId = Long.min(user, friend);
        Relationship currentRelationship = getCurrentRelationship(lowerId);
        Relationship newRelationShip;
        switch (currentRelationship) {
            case UNCONFIRMED: {
                if (lowerId == user) {
                    newRelationShip = CONFIRM_BY_USER;
                } else {
                    newRelationShip = CONFIRM_BY_FRIEND;
                }
            }
            case CONFIRM_BY_USER:
                if (lowerId == friend) {
                    newRelationShip = CONFIRM;
                } 
            case CONFIRM_BY_FRIEND:
                if (lowerId == user) {
                newRelationShip = CONFIRM;
            } 
            default:;
        }
    }

    public void deleteRelationship(long user, long friend) {
        long lowerId = Long.min(user, friend);
        Relationship currentRelationship = getCurrentRelationship(lowerId);
        Relationship newRelationShip;
        switch (currentRelationship) {
            case CONFIRM: {
                if (lowerId == user) {
                    newRelationShip = CONFIRM_BY_FRIEND;
                } else {
                    newRelationShip = CONFIRM_BY_USER;
                }
            }
            case CONFIRM_BY_USER:
                if (lowerId == friend) {
                    newRelationShip = UNCONFIRMED;
                }
            case CONFIRM_BY_FRIEND:
                if (lowerId == user) {
                    newRelationShip = UNCONFIRMED;
                }
            default:;
        }
    }
}
