package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    Integer id;
    String email;
    String login;
    String name;
    LocalDate birthday;

}

//TODO аннотации на email, name, id
