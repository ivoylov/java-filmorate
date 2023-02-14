package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private static final int MAX_LENGTH_FILM_DESCRIPTION = 200;
    private static final LocalDate MINIMAL_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    Integer id;
    @NotBlank(message = "Пустое имя фильма")
    String name;
    @Length(max = MAX_LENGTH_FILM_DESCRIPTION, message = "Максимальная длина описания больше " + MAX_LENGTH_FILM_DESCRIPTION)
    String description;
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    Long duration;

    public static LocalDate getMinimalReleaseDate() {
        return MINIMAL_RELEASE_DATE;
    }

}