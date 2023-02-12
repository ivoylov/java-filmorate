package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;

public class FilmControllerTest {

    @Test
    void notValidateBlankName() {
        assertThrows(FilmValidationException.class, () -> {
            FilmController filmController = new FilmController();
            filmController.addFilm(getFilmWithoutName());
        });
    }

    @Test
    void notValidateBigDescription() {
        assertThrows(FilmValidationException.class, () -> {
            FilmController filmController = new FilmController();
            filmController.addFilm(getFilmWithBigDescription());
        });
    }

    @Test
    void notValidateIncorrectReleaseDate() {
        assertThrows(FilmValidationException.class, () -> {
            FilmController filmController = new FilmController();
            filmController.addFilm(getFilmWithIncorrectReleaseDate());
        });
    }

    @Test
    void notValidateZeroDuration() {
        assertThrows(FilmValidationException.class, () -> {
            FilmController filmController = new FilmController();
            filmController.addFilm(getFilmWithZeroDuration());
        });
    }

    private Film getFilmWithoutName() {
        return Film.builder()
                .name("")
                .description("описание")
                .duration(Duration.ofMinutes(100))
                .id(1)
                .releaseDate(LocalDate.of(2023,2,12))
                .build();
    }

    private Film getFilmWithBigDescription() {
        String description = "a".repeat(Math.max(0, new FilmController().getMaxLengthFilmDescription() + 1));
        return Film.builder()
                .name("Имя")
                .description(description)
                .duration(Duration.ofMinutes(100))
                .id(1)
                .releaseDate(LocalDate.of(2023,2,12))
                .build();
    }

    private Film getFilmWithIncorrectReleaseDate() {
        LocalDate incorrectDate = new FilmController().getMinimalReleaseDate().minusDays(1);
        return Film.builder()
                .name("Имя")
                .description("Описание")
                .duration(Duration.ofMinutes(100))
                .id(1)
                .releaseDate(incorrectDate)
                .build();
    }

    private Film getFilmWithZeroDuration() {
        return Film.builder()
                .name("Имя")
                .description("Описание")
                .duration(Duration.ofMinutes(0))
                .id(1)
                .releaseDate(LocalDate.of(2023,2,12))
                .build();
    }

}
