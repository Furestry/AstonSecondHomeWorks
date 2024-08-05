package ru.furestry.astonhomework.service;

import ru.furestry.astonhomework.entity.Department;
import ru.furestry.astonhomework.repository.DepartmentRepository;

public class DepartmentService extends SimpleLongIdService<Department> {
    private static DepartmentService instance;

    public DepartmentService() {
        this.repository = new DepartmentRepository();
        list = repository.findAll();
    }

    public static DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentService();
        }

        return instance;
    }
}
