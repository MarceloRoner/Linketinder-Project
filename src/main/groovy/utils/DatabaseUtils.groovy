package utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseUtils {

    static final String URL = "jdbc:postgresql://localhost:5432/linketinder"
    static final String USER = "marcelo"
    static final String PASSWORD = "jack"

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}
