package ru.furestry.astonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements IEntity {
    private Long id;

    private String username;

    private Department department;

    private Collection<Role> roles = new ArrayList<>();

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(Long id, String username, Department department) {
        this.id = id;
        this.username = username;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof User user)) return false;

        return user.getId().equals(id) && user.getUsername().equals(username);
    }
}
