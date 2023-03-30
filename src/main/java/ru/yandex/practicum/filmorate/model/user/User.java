package ru.yandex.practicum.filmorate.model.user;

import javax.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@Builder
public class User {

    private Long id;
    @Email(message = "Некорректный адрес электронной почты")
    private String email;
    @NotBlank(message = "Пустой логин")
    private String login;
    private String name;
    @PastOrPresent(message = "День рождения в будущем")
    private LocalDate birthday;
    private final HashSet<Long> friends = new HashSet<>();

    public void addFriend(long userId) {
        friends.add(userId);
    }

    public void deleteFriend(long userId) {
        friends.remove(userId);
    }

    public HashSet<Long> getAllFriends() {
        return new HashSet<>(friends);
    }

}
