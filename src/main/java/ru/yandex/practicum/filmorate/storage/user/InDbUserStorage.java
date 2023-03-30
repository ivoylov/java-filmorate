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
        return jdbcTemplate.queryForObject(
                "SELECT FILMORATE_USER_ID FROM filmorate_user WHERE login = ?",
                Long.class,
                login);
    }

    private Relationship getCurrentRelationship(long userId, long friendId) {
        Integer currentStatusId;
        try {
            currentStatusId = jdbcTemplate.queryForObject(
                    "SELECT status_id FROM relationship WHERE user_id = ? AND friend_id = ?",
                    Integer.class,
                    userId, friendId);
        } catch (Exception e) {
            return NOT_EXIST;
        }
        return Relationship.getRelationship(currentStatusId);
    }

    public void createRelationship(long userId, long friendId) {
        long lowerId = Long.min(userId, friendId);
        Relationship currentRelationship = getCurrentRelationship(userId, friendId);
        Relationship newRelationShip;
        switch (currentRelationship) {
            case NOT_EXIST:
                if (lowerId == userId) {
                    newRelationShip = CONFIRM_BY_USER;
                } else {
                    newRelationShip = CONFIRM_BY_FRIEND;
                }
                insertRelationship(userId, friendId, newRelationShip);
                break;
            case UNCONFIRMED:
                if (lowerId == userId) {
                    newRelationShip = CONFIRM_BY_USER;
                } else {
                    newRelationShip = CONFIRM_BY_FRIEND;
                }
                updateRelationship(userId, friendId, newRelationShip);
                break;
            case CONFIRM_BY_USER:
                if (lowerId == friendId) {
                    newRelationShip = CONFIRM;
                } else {
                    newRelationShip = CONFIRM_BY_USER;
                }
                updateRelationship(userId, friendId, newRelationShip);
                break;
            case CONFIRM_BY_FRIEND:
                if (lowerId == userId) {
                    newRelationShip = CONFIRM;
                } else {
                    newRelationShip = CONFIRM_BY_FRIEND;
                }
                updateRelationship(userId, friendId, newRelationShip);
        }
    }

    public void deleteRelationship(long userId, long friendId) {
        long lowerId = Long.min(userId, friendId);
        Relationship currentRelationship = getCurrentRelationship(userId, friendId);
        Relationship newRelationShip;
        switch (currentRelationship) {
            case CONFIRM:
                if (lowerId == userId) {
                    newRelationShip = CONFIRM_BY_FRIEND;
                } else {
                    newRelationShip = CONFIRM_BY_USER;
                }
                updateRelationship(userId, friendId, newRelationShip);
                break;
            case CONFIRM_BY_USER:
                if (lowerId == friendId) {
                    newRelationShip = UNCONFIRMED;
                } else {
                    newRelationShip = CONFIRM_BY_USER;
                }
                updateRelationship(userId, friendId, newRelationShip);
                break;
            case CONFIRM_BY_FRIEND:
                if (lowerId == userId) {
                    newRelationShip = UNCONFIRMED;
                } else {
                    newRelationShip = CONFIRM_BY_FRIEND;
                }
                updateRelationship(userId, friendId, newRelationShip);
                break;
        }
    }

    private void insertRelationship(long userId, long friendId, Relationship relationship) {
        String query = "INSERT " +
                "INTO relationship " +
                "(user_id, friend_id, status_id) " +
                "VALUES (?,?,?)";
        jdbcTemplate.update(query, userId, friendId, relationship.ordinal());
    }

    private void updateRelationship(long userId, long friendId, Relationship relationship) {
        String query = "UPDATE relationship " +
                "SET " +
                "status_id = ? " +
                "WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(query, relationship.ordinal(), userId, friendId);
    }

    public ArrayList<Long> findFriends(long userId) {
        return new ArrayList<Long>(
                jdbcTemplate.query("SELECT friend_id " +
                                "FROM relationship " +
                                "WHERE user_id = ?",
                longRowMapper, userId));
    }

    private final RowMapper<Long> longRowMapper = (recordSet, rowNumber) -> recordSet.getLong("friend_id");

}
