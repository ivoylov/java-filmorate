package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    Integer id;
    @Email(message = "Некорректный адрес электронной почты")
    String email;
    @NotBlank(message = "Пустой логин")
    String login;
    String name;
    @PastOrPresent
    LocalDate birthday;

}

//TODO аннотации на email, name, id
