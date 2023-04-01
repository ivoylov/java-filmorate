package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;

public interface Storage<T> {

    T create(T t);

    T update(T t);

    T find(long id);

    ArrayList<T> findAll();

    void delete(long id);
    
    boolean isExist(long id);

}
