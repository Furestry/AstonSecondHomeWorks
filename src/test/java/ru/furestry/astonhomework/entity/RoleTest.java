package ru.furestry.astonhomework.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RoleTest {
    @Test
    public void testEquals() {
        Long id = 1L;
        String name = "role";
        Role r1 = new Role(id, name, new ArrayList<>());
        Role r2 = new Role(id, name);
        Role r3 = new Role(id, "newRole");

        Assertions.assertEquals(r1, r2);
        Assertions.assertNotEquals(r1, r3);
    }

    @Test
    public void testHashcode() {
        Long id = 1L;
        String name = "user";
        Role u1 = new Role(id, name, new ArrayList<>());
        Role u2 = new Role(id, name);
        Role u3 = new Role(id, "newRole");

        Assertions.assertEquals(u1.hashCode(), u2.hashCode());
        Assertions.assertNotEquals(u1.hashCode(), u3.hashCode());
    }
}
