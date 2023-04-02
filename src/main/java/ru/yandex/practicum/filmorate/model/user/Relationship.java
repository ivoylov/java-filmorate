package ru.yandex.practicum.filmorate.model.user;

public enum Relationship {
    NOT_EXIST(0),
    UNCONFIRMED(1),
    CONFIRM_BY_USER(2),
    CONFIRM_BY_FRIEND(3),
    CONFIRM(4);

    private final int statusId;

    Relationship(int statusId) {
        this.statusId = statusId;
    }

    public int getId() {
        return statusId;
    }

    public static Relationship getRelationship(int id) {
        switch (id) {
            case 1 : return UNCONFIRMED;
            case 2 : return CONFIRM_BY_USER;
            case 3 : return CONFIRM_BY_FRIEND;
            case 4 : return CONFIRM;
            default: return NOT_EXIST;
        }
    }

}
