package com.example.lab7_starsgallery.dao;

import java.util.List;

public interface IDao<T> {
    boolean create(T item);
    boolean update(T item);
    boolean remove(T item);
    T findById(int id);
    List<T> getAll();
}
