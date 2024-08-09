package ru.furestry.astonhomework.repository;

import org.hibernate.Session;
import ru.furestry.astonhomework.entity.User;

import java.util.List;
import java.util.Optional;

public class UserRepository extends SimpleLongRepository<User> {
    @Override
    public Optional<User> findById(Long id) {
        try (Session session = createSession()) {
            return session
                    .createQuery("from User U where U.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = createSession()) {
            return session.createQuery("select U from User U", User.class)
                    .list();
        }
    }
}
