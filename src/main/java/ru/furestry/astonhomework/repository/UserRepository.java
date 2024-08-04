package ru.furestry.astonhomework.repository;

import ru.furestry.astonhomework.database.DatabaseFactory;
import ru.furestry.astonhomework.entity.Department;
import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository implements IRepository<User, Long> {
    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT u.username, u.department_id, d.name AS d_name, r.id, r.name AS r_name, ur.role_id FROM users u " +
                "LEFT JOIN departments d ON u.department_id = d.id " +
                "LEFT JOIN user_roles ur ON ur.user_id = u.id " +
                "LEFT JOIN roles r ON r.id = ur.role_id " +
                "WHERE u.id = ?";
        String username = null;
        Department department = null;
        List<Role> roles = new ArrayList<>();

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result == null) {
                return Optional.empty();
            }

            if (result.next()) {
                username = result.getString("username");

                if (result.getLong("department_id") != 0L) {
                    department = new Department(
                            result.getLong("department_id"),
                            result.getString("d_name")
                    );
                }

                if (result.getLong("role_id") != 0L) {
                    do {
                        roles.add(new Role(
                                result.getLong("role_id"),
                                result.getString("r_name")
                        ));
                    } while (result.next());
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.of(new User(id, username, department, roles));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id FROM users";
        List<User> users;

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            if (result == null) {
                return List.of();
            }

            users = new ArrayList<>();
            while (result.next()) {
                Optional<User> user = findById(result.getLong("id"));

                user.ifPresent(users::add);
            }

            users.sort(Comparator.comparingLong(User::getId));

            return users;
        } catch (SQLException e) {
            return List.of();
        }
    }

    @Override
    public boolean save(User entity) {
        StringBuilder saveUser = new StringBuilder("INSERT INTO users (username, department_id) VALUES (?, ?);");
        saveUser.append("INSERT INTO user_roles (user_id, role_id) VALUES(?, ?);".repeat(entity.getRoles().size()));

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(saveUser.toString());
            preparedStatement.setString(1, entity.getUsername());

            AtomicInteger paramIndex = new AtomicInteger(2);
            entity.getRoles().forEach(r -> {
                try {
                    preparedStatement.setLong(paramIndex.incrementAndGet(), entity.getId());
                    preparedStatement.setLong(paramIndex.incrementAndGet(), r.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            if (entity.getDepartment() == null) {
                preparedStatement.setNull(2, Types.BIGINT);
            } else {
                preparedStatement.setLong(2, entity.getDepartment().getId());
            }

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(User entity) {
        StringBuilder sql = new StringBuilder("UPDATE users SET username = ?, department_id = ? WHERE id = ?;");
        sql.append("DELETE FROM user_roles WHERE user_id = ?;");
        sql.append("INSERT INTO user_roles (user_id, role_id) values (?, ?);"
                .repeat(entity.getRoles().size())
        );

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            preparedStatement.setString(1, entity.getUsername());

            if (entity.getDepartment() == null) {
                preparedStatement.setNull(2, Types.BIGINT);
            } else {
                preparedStatement.setLong(2, entity.getDepartment().getId());
            }

            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setLong(4, entity.getId());

            AtomicInteger paramIndex = new AtomicInteger(4);
            entity.getRoles().forEach(r -> {
                try {
                    preparedStatement.setLong(paramIndex.incrementAndGet(), entity.getId());
                    preparedStatement.setLong(paramIndex.incrementAndGet(), r.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(User entity) {
        return delete(entity.getId());
    }
}
