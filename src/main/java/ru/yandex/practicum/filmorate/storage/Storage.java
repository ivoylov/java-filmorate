package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;
import java.util.Optional;

public interface Storage<T> {

    void create(T t);
    void update(T t);
    T find(long id);
    ArrayList<T> findAll();
    void delete(long id);
    boolean isExist(long id);

}
