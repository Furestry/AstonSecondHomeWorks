package ru.furestry.astonhomework.service;

import ru.furestry.astonhomework.entity.User;
import ru.furestry.astonhomework.repository.UserRepository;

import java.util.List;

public class UserService extends SimpleLongIdService<User> {
    private static UserService instance;

    public UserService() {
        this.repository = new UserRepository();
        list = repository.findAll();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public List<User> findAllByDepartmentId(Long id) {
        return list.parallelStream()
                .filter(u -> u.getDepartment() != null)
                .filter(u -> u.getDepartment().getId().equals(id))
                .toList();
    }
}
