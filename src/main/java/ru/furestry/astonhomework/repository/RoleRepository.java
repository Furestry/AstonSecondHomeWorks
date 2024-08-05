package ru.furestry.astonhomework.repository;

import ru.furestry.astonhomework.database.DatabaseFactory;
import ru.furestry.astonhomework.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RoleRepository  implements IRepository<Role, Long> {
    @Override
    public Optional<Role> findById(Long id) {
        String sql = "SELECT name FROM roles WHERE id = ?";
        String name = null;

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result == null) {
                return Optional.empty();
            }

            if (result.next()) {
                name = result.getString("name");
            }
        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.of(new Role(id, name));
    }

    @Override
    public List<Role> findAll() {
        String sql = "SELECT id, name FROM roles";
        List<Role> roles;

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            if (result == null) {
                return List.of();
            }

            roles = new ArrayList<>();
            while (result.next()) {
                roles.add(new Role(
                        result.getLong("id"),
                        result.getString("name")
                ));
            }

            roles.sort(Comparator.comparingLong(Role::getId));

            return roles;
        } catch (SQLException e) {
            return List.of();
        }
    }

    @Override
    public boolean save(Role entity) {
        String sql = "INSERT INTO roles (name) VALUES (?)";

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());

            boolean isSaved = preparedStatement.executeUpdate() > 0;

            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                entity.setId(result.getLong("id"));
            }

            return isSaved;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Role entity) {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";

        try {
            Connection conn = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM roles WHERE id = ?";

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
    public boolean delete(Role entity) {
        return delete(entity.getId());
    }
}
