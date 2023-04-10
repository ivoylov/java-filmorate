package ru.yandex.practicum.filmorate.model.film;

import javax.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Film {

    public static final int MAX_LENGTH_FILM_DESCRIPTION = 200;
    public static final LocalDate MINIMAL_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public Film(Long id, String name, String description, LocalDate releaseDate, Long duration, Mpa mpa, List<Genre> genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genre;
    }

    private Long id;
    @NotBlank(message = "Пустое имя фильма")
    private String name;
    @Length(max = MAX_LENGTH_FILM_DESCRIPTION, message = "Максимальная длина описания больше " + MAX_LENGTH_FILM_DESCRIPTION)
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private Long duration;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres;

}