package ru.furestry.astonhomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.furestry.astonhomework.entity.Role;

import java.util.Optional;

public class RoleServiceTest {
    private final RoleService roleService = new RoleService();

    @Test
    public void findByIdTest() {
        Optional<Role> emptyRole = roleService.findById(-1L);
        Optional<Role> findRole = roleService.findById(1L);

        Assertions.assertTrue(emptyRole.isEmpty());
        Assertions.assertTrue(findRole.isPresent());
    }

    @Test
    public void findAllTest() {
        Assertions.assertFalse(roleService.findAll().isEmpty());
    }

    @Test
    public void saveAndDeleteEntityTest() {
        Role role = new Role();
        role.setName("r_test123");

        Assertions.assertTrue(roleService.save(role));
        Assertions.assertTrue(roleService.delete(role));
    }

    @Test
    public void saveAndDeleteByIdTest() {
        Role role = new Role();
        role.setName("r_test1253");

        Assertions.assertTrue(roleService.save(role));
        Assertions.assertTrue(roleService.delete(role.getId()));
    }

    @Test
    public void saveUpdateAndDeleteEntityTest() {
        Role role = new Role();
        role.setName("r_test12356");

        Assertions.assertTrue(roleService.save(role));

        Assertions.assertTrue(roleService.update(role));
        Assertions.assertTrue(roleService.delete(role));
    }
}
