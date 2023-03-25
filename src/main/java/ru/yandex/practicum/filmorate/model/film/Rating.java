package ru.yandex.practicum.filmorate.model.film;

public enum Rating {
    G,
    PG,
    PG13,
    R,
    NC17;

    public static Rating getRating(int id) {
        switch (id) {
            case 1: return G;
            case 2: return PG;
            case 3: return PG13;
            case 4: return R;
            case 5: return NC17;
        }
        return null;
    }

}
