package warehouse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {
    Connection getConnection() throws SQLException {
        final String DBMS_CONN_STRING = "jdbc:mysql://localhost:3306/warehouse";
        final String DBMS_USERNAME = "root";
        final String DBMS_PASSWORD = "kisame11sql";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {}
        return DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
    }
}
