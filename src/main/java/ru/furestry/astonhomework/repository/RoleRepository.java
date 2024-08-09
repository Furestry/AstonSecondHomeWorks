package ru.furestry.astonhomework.repository;

import org.hibernate.Session;
import ru.furestry.astonhomework.entity.Role;

import java.util.*;

public class RoleRepository  extends SimpleLongRepository<Role> {
    @Override
    public Optional<Role> findById(Long id) {
        try (Session session = createSession()) {
            return session
                    .createQuery("from Role R where R.id = :id", Role.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Role> findAll() {
        try (Session session = createSession()) {
            return session.createQuery("select R from Role R", Role.class)
                    .list();
        }
    }
}
