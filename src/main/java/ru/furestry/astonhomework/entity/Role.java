package ru.furestry.astonhomework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    private Collection<User> users;

    public Role(Long id, String name) {
        this(id, name, new ArrayList<>());
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
        if (!(o instanceof Role role)) return false;
        if (id == null) {
            if (role.getId() == null) {
                return role.getName().equals(name);
            }

            return false;
        }
        if (role.getId() == null) {
            return false;
        }

        return role.getId().equals(id) && role.getName().equals(name);
    }

    @JsonProperty("users")
    public int getUsersSize() {
        return users.size();
    }
}
