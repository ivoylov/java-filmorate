package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@Builder
public class User {

    Long id;
    @Email(message = "Некорректный адрес электронной почты")
    String email;
    @NotBlank(message = "Пустой логин")
    String login;
    String name;
    @PastOrPresent(message = "День рождения в будущем")
    LocalDate birthday;
    private HashSet<Long> friends;

    public void addFriend(long userId) {
        friends.add(userId);
    }

    public void deleteFriend(long userId) {
        friends.remove(userId);
    }

    public boolean isFriendsExist() {
        return friends != null;
    }

    public void setFriends() {
        friends = new HashSet<>();
    }

    public HashSet<Long> getAllFriends() {
        return new HashSet<>(friends);
    }

}
