package ru.furestry.astonhomework.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DepartmentTest {
    private static final Long testId = 1L;
    private static final String testName = "department";

    @Test
    public void testEquals() {
        Department d1 = new Department(testId, testName, null);
        Department d2 = new Department(testId, testName);
        Department d3 = new Department(testId, "newDepartment");
        Department d4 = new Department(5L, testName);

        Assertions.assertEquals(d1, d2);
        Assertions.assertNotEquals(d1, d3);
        Assertions.assertNotEquals(d1, d4);
        Assertions.assertNotEquals(d3, d4);
    }

    @Test
    public void testHashcode() {
        Department d1 = new Department(testId, testName, null);
        Department d2 = new Department(testId, testName);
        Department d3 = new Department(testId, "newDep");
        Department d4 = new Department(5L, testName);

        Assertions.assertEquals(d1.hashCode(), d2.hashCode());
        Assertions.assertNotEquals(d1.hashCode(), d3.hashCode());
        Assertions.assertNotEquals(d1.hashCode(), d4.hashCode());
        Assertions.assertNotEquals(d3.hashCode(), d4.hashCode());
    }
}
