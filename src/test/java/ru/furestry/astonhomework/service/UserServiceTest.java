package ru.furestry.astonhomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.furestry.astonhomework.entity.User;

import java.util.Optional;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void findByIdTest() {
        Optional<User> emptyUser = userService.findById(-1L);

        //There is no user with id = 1 in my database
        Optional<User> findUser = userService.findById(22L);

        Assertions.assertTrue(emptyUser.isEmpty());
        Assertions.assertTrue(findUser.isPresent());
    }

    @Test
    public void findAllTest() {
        Assertions.assertFalse(userService.findAll().isEmpty());
    }

    @Test
    public void findAllByDepartmentIdTest() {
        Assertions.assertFalse(userService.findAllByDepartmentId(1L).isEmpty());
    }

    @Test
    public void saveAndDeleteEntityTest() {
        User user = new User();
        user.setUsername("test123");

        Assertions.assertTrue(userService.save(user));
        Assertions.assertTrue(userService.delete(user));
    }

    @Test
    public void saveAndDeleteByIdTest() {
        User user = new User();
        user.setUsername("test1253");

        Assertions.assertTrue(userService.save(user));
        Assertions.assertTrue(userService.delete(user.getId()));
    }

    @Test
    public void saveUpdateAndDeleteEntityTest() {
        User user = new User();
        user.setUsername("test12356");

        Assertions.assertTrue(userService.save(user));

        user.setDepartment(DepartmentService.getInstance().findById(1L).orElse(null));

        Assertions.assertTrue(userService.update(user));
        Assertions.assertTrue(userService.delete(user));
    }
}
