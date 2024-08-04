package ru.furestry.astonhomework.service;

import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.repository.RoleRepository;

public class RoleService extends SimpleLongIdService<Role> {
    private static RoleService instance;

    public RoleService() {
        this.repository = new RoleRepository();
        list = repository.findAll();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }

        return instance;
    }
}
