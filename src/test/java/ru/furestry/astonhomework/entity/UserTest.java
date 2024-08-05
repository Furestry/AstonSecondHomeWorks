package ru.furestry.astonhomework.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testEquals() {
        Long id = 1L;
        String name = "user";
        User u1 = new User(id, name, null);
        User u2 = new User(id, name);
        User u3 = new User(id, "newUser");

        Assertions.assertEquals(u1, u2);
        Assertions.assertNotEquals(u1, u3);
    }

    @Test
    public void testHashcode() {
        Long id = 1L;
        String name = "user";
        User u1 = new User(id, name, null);
        User u2 = new User(id, name);
        User u3 = new User(id, "newUser");

        Assertions.assertEquals(u1.hashCode(), u2.hashCode());
        Assertions.assertNotEquals(u1.hashCode(), u3.hashCode());
    }
}
