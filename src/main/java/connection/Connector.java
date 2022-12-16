package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Connector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hr_department";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    private Connector() {}

    public static Connection getConnection() {

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}