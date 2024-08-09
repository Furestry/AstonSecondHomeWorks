package ru.furestry.astonhomework.repository;

import org.hibernate.Session;
import ru.furestry.astonhomework.entity.Department;

import java.util.List;
import java.util.Optional;

public class DepartmentRepository extends SimpleLongRepository<Department> {
    @Override
    public Optional<Department> findById(Long id) {
        try (Session session = createSession()) {
            return session
                    .createQuery("from Department U where D.id = :id", Department.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Department> findAll() {
        try (Session session = createSession()) {
            return session.createQuery("select D from Department D", Department.class)
                    .list();
        }
    }
}
