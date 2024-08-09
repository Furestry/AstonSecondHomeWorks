package ru.furestry.astonhomework.database;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.furestry.astonhomework.entity.Department;
import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.entity.User;

public class DatabaseFactory {
    private static StandardServiceRegistry registry;
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            String cfgFile = "hibernate.cfg.postgresql.xml";
            String connAddress = "jdbc:postgresql://" + System.getenv("AH_DB_URL");

            Configuration configuration = new Configuration()
                    .configure(cfgFile)
                    .setProperty("hibernate.connection.url", connAddress)
                    .setProperty("hibernate.connection.username", System.getenv("AH_DB_USER"))
                    .setProperty("hibernate.connection.password", System.getenv("AH_DB_PASSWORD"))
                    .addAnnotatedClass(Department.class)
                    .addAnnotatedClass(Role.class)
                    .addAnnotatedClass(User.class);
            registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(registry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
