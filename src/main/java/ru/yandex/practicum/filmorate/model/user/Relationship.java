package ru.yandex.practicum.filmorate.model.user;

public enum Relationship {
    UNCONFIRMED,
    CONFIRM_BY_USER,
    CONFIRM_BY_FRIEND,
    CONFIRM,
    UNKNOWN;

    public static Relationship getRelationship(int id) {
        switch (id) {
            case 1 : return UNCONFIRMED;
            case 2 : return CONFIRM_BY_USER;
            case 3 : return CONFIRM_BY_FRIEND;
            case 4 : return CONFIRM;
            default: return UNKNOWN;
        }
    }

}
