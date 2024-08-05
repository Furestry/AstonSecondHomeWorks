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
    public int hashCode(){
        int result = 17;

        if (id != null) {
            result = 31 * result + id.hashCode();
        }
        if (username != null) {
            result = 31 * result + username.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof User user)) return false;
        if (id == null) {
            if (user.getId() == null) {
                return user.getUsername().equals(username);
            }

            return false;
        }
        if (user.getId() == null) {
            return false;
        }

        return user.getId().equals(id) && user.getUsername().equals(username);
    }
}
