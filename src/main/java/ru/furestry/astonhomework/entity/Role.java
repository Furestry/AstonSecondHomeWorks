package ru.furestry.astonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements IEntity {
    private Long id;

    private String name;

    private Collection<User> users;

    public Role(Long id, String name) {
        this(id, name, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Role role)) return false;

        return role.getId().equals(id) && role.getName().equals(name);
    }
}
