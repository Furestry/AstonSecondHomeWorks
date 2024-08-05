package ru.furestry.astonhomework.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseFactory {
    private static DatabaseFactory instance;
    private final DataSource dataSource;

    public DatabaseFactory() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setURL("jdbc:postgresql://" + System.getenv("AH_DB_URL"));
        dataSource.setUser(System.getenv("AH_DB_USER"));
        dataSource.setPassword(System.getenv("AH_DB_PASSWORD"));

        this.dataSource = dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return DatabaseFactory.getInstance().dataSource.getConnection();
    }

    public static DatabaseFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseFactory();
        }

        return instance;
    }
}
