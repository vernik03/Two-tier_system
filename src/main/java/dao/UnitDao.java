package dao;

import connection.Connector;
import models.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class UnitDao {
    public Optional<Unit> findById(int id) {
        final String sql = "SELECT * FROM units WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Unit> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Unit unit = new Unit();
                    unit.setId(rs.getInt("id"));
                    unit.setName(rs.getString("name"));
                    result = Optional.of(unit);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Unit> findAll() {
        final String sql = "SELECT * FROM units";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Unit> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Unit unit = new Unit();
                    unit.setId(rs.getInt("id"));
                    unit.setName(rs.getString("name"));
                    result.add(unit);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Unit updated) {
        final String sql = "UPDATE units SET name = ? WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, updated.getName());
            statement.setInt(2, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM units WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Unit toInsert) {

        final String sql = "INSERT INTO units(name) VALUES(?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, toInsert.getName());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}