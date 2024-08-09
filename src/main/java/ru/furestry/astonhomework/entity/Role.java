package ru.furestry.astonhomework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Collection<User> users;

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
        if (this == o) return true;
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
