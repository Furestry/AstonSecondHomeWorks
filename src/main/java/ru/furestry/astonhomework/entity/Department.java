package ru.furestry.astonhomework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class Department implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="department")
    @ToString.Exclude
    private List<User> users;

    public Department(Long id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Department(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
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
        if (this == o) return true;
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

    @JsonProperty("users")
    public int getUsersSize() {
        return users.size();
    }
}
