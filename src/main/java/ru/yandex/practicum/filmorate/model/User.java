package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@Builder
public class User {

    long id;
    @Email(message = "Некорректный адрес электронной почты")
    String email;
    @NotBlank(message = "Пустой логин")
    String login;
    String name;
    @PastOrPresent(message = "День рождения в будущем")
    LocalDate birthday;
    private Set<Long> friends;

    public void addFriend(long userId) {
        friends.add(userId);
    }

    public void deleteFriend(long userId) {
        friends.remove(userId);
    }

    public List<Long> getAllFriends() {
        return new ArrayList<>(friends);
    }

}
