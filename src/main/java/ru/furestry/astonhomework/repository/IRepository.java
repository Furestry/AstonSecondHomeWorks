package ru.furestry.astonhomework.repository;

import ru.furestry.astonhomework.entity.IEntity;

import java.util.List;
import java.util.Optional;

public interface IRepository <T extends IEntity, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(ID id);

    boolean delete(T entity);
}
