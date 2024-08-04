package ru.furestry.astonhomework.service;

import ru.furestry.astonhomework.entity.IEntity;
import ru.furestry.astonhomework.repository.IRepository;

import java.util.List;
import java.util.Optional;

public abstract class SimpleLongIdService<T extends IEntity> implements IService<T, Long> {
    protected IRepository<T, Long> repository;
    protected List<T> list;

    @Override
    public Optional<T> findById(Long id) {
        return list.parallelStream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public List<T> findAll() {
        return list;
    }

    @Override
    public boolean delete(T entity) {
        list.remove(entity);

        return repository.delete(entity);
    }

    @Override
    public boolean delete(Long id) {
        findById(id).ifPresent(list::remove);

        return repository.delete(id);
    }

    @Override
    public boolean save(T entity) {
        list.add(entity);

        return repository.save(entity);
    }

    @Override
    public boolean update(T entity) {
        if (list.contains(entity)) {
            list.set(list.indexOf(entity), entity);
        } else {
            list.add(entity);
        }

        return repository.update(entity);
    }

    public void saveAll() {
        list.forEach(this::save);
    }
}
