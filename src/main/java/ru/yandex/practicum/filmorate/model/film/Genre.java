package ru.yandex.practicum.filmorate.model.film;

public enum Genre {

    COMEDY,
    DRAMA,
    CARTOON,
    THRILLER,
    DOCUMENTARY,
    ACTION;

    public static Genre getGenre(int id) {
        switch (id) {
            case 1:
                return COMEDY;
            case 2:
                return DRAMA;
            case 3:
                return CARTOON;
            case 4:
                return THRILLER;
            case 5:
                return DOCUMENTARY;
            case 6:
                return ACTION;
        }
        return null;
    }

}