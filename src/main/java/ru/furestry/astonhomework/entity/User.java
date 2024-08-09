package ru.furestry.astonhomework.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.furestry.astonhomework.entity.document.Document;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user")
    private Document document;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles = new ArrayList<>();

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
        if (this == o) return true;
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
