package com.butchjgo.dao;

import java.util.List;

public interface DAO<T> {
    void create(T t);

    void updateById(String id);

    void deleteById(String id);

    T searchById(String id);

    List<T> getAll();
}
