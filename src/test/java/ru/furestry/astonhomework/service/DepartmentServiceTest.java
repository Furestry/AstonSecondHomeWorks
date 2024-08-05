package ru.furestry.astonhomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.furestry.astonhomework.entity.Department;

import java.util.Optional;

public class DepartmentServiceTest {
    private final DepartmentService departmentService = new DepartmentService();

    @Test
    public void findByIdTest() {
        Optional<Department> emptyDepartment = departmentService.findById(-1L);
        Optional<Department> findDepartment = departmentService.findById(1L);

        Assertions.assertTrue(emptyDepartment.isEmpty());
        Assertions.assertTrue(findDepartment.isPresent());
    }

    @Test
    public void findAllTest() {
        Assertions.assertFalse(departmentService.findAll().isEmpty());
    }

    @Test
    public void saveAndDeleteEntityTest() {
        Department department = new Department();
        department.setName("d_test123");

        Assertions.assertTrue(departmentService.save(department));
        Assertions.assertTrue(departmentService.delete(department));
    }

    @Test
    public void saveAndDeleteByIdTest() {
        Department department = new Department();
        department.setName("d_test1253");

        Assertions.assertTrue(departmentService.save(department));
        Assertions.assertTrue(departmentService.delete(department.getId()));
    }

    @Test
    public void saveUpdateAndDeleteEntityTest() {
        Department department = new Department();
        department.setName("d_test12356");

        Assertions.assertTrue(departmentService.save(department));

        Assertions.assertTrue(departmentService.update(department));
        Assertions.assertTrue(departmentService.delete(department));
    }
}
