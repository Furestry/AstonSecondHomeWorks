package ru.furestry.astonhomework.service;

import ru.furestry.astonhomework.entity.IEntity;

import java.util.List;
import java.util.Optional;

public interface IService<T extends IEntity, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    boolean delete(T entity);

    boolean delete(ID id);

    boolean save(T entity);

    boolean update(T entity);

    void saveAll();
}
