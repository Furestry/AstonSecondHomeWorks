package ru.furestry.astonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements IEntity {
    private Long id;

    private String name;

    private List<User> users;

    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode(){
        int result = 17;

        if (id != null) {
            result = 31 * result + id.hashCode();
        }

        if (name != null) {
            result = 31 * result + name.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Department department)) return false;
        if (id == null) {
            if (department.getId() == null) {
                return department.getName().equals(name);
            }

            return false;
        }
        if (department.getId() == null) {
            return false;
        }

        return department.getId().equals(id) && department.getName().equals(name);
    }
}
