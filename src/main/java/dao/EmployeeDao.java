package dao;

import connection.Connector;
import models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EmployeeDao {
    public Optional<Employee> findById(int id) {
        final String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Employee> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (((ResultSet) rs).next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("id"));
                    employee.setUnitId(rs.getInt("unit_id"));
                    employee.setSalary(rs.getInt("salary"));
                    employee.setName(rs.getString("name"));
                    result = Optional.of(employee);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> findAll() {
        final String sql = "SELECT * FROM employees";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Employee> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("id"));
                    employee.setUnitId(rs.getInt("unit_id"));
                    employee.setName(rs.getString("name"));
                    employee.setSalary(rs.getInt("salary"));
                    result.add(employee);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Employee updated) {
        final String sql = "UPDATE employees SET unit_id = ?, name = ?, salary = ? WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, updated.getUnitId());
            statement.setString(2, updated.getName());
            statement.setInt(3, updated.getSalary());
            statement.setInt(4, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM hr_department.employees WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Employee toInsert) {
        final String sql = "INSERT INTO hr_department.employees(unit_id, name, salary) VALUES(?, ?, ?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, toInsert.getUnitId());
            statement.setString(2, toInsert.getName());
            statement.setInt(3, toInsert.getSalary());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Employee> findByUnitName(String unitName) {
        final String sql = """
            SELECT * FROM hr_department.employees WHERE unit_id = (
                SELECT id FROM hr_department.units WHERE units.name = ?
            )
        """;

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, unitName);
            List<Employee> result;

            try (ResultSet rs = statement.executeQuery()) {
                result = getEmployeesFromResultSet(rs);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean moveToAnotherUnit(int employeeId, int newUnitID) {
        Optional<Employee> optionalEmployee = findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            return false;
        }

        Employee employee = optionalEmployee.get();
        employee.setUnitId(newUnitID);
        update(employee);

        return true;
    }

    private List<Employee> getEmployeesFromResultSet(ResultSet rs) throws SQLException {
        List<Employee> result = new ArrayList<>();

        while (rs.next()) {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setUnitId(rs.getInt("unit_id"));
            employee.setSalary(rs.getInt("salary"));
            employee.setName(rs.getString("name"));;
            result.add(employee);
        }

        return result;
    }
}