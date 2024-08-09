package ru.furestry.astonhomework.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.furestry.astonhomework.database.DatabaseFactory;
import ru.furestry.astonhomework.entity.IEntity;

public abstract class SimpleLongRepository<T extends IEntity> implements IRepository<T, Long> {
    private final Logger log = LoggerFactory.getLogger(SimpleLongRepository.class);

    @Override
    public boolean save(T obj) {
        Session session = createSession();
        session.merge(obj);
        session.getTransaction().commit();
        session.close();

        return obj != null;
    }

    @Override
    public boolean delete(T obj) {
        Session session = createSession();
        session.remove(obj);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Long id) {
        return delete(findById(id).orElse(null));
    }

    @Override
    public boolean update(T obj) {
        Session session = createSession();
        session.merge(obj);
        session.getTransaction().commit();
        session.close();

        return obj != null;
    }

    protected Session createSession() {
        Session session = null;

        try {
            session = DatabaseFactory.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (HibernateException e) {
            log.error(e.getMessage());
        }

        return session;
    }
}
