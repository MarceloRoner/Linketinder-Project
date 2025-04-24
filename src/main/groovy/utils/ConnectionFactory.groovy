package utils

import java.sql.Connection
import java.sql.SQLException
import java.sql.DriverManager


class ConnectionFactory {

    private static final String URL  = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String USER = "marcelo"
    private static final String PASSWORD = "jack"

    private static final ConnectionFactory INSTANCE = new ConnectionFactory()
    static ConnectionFactory getInstance() { return INSTANCE }

    private Connection connection

    private ConnectionFactory() { }

    Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD)
        }
        return connection
    }

    void close() {
        if (connection && !connection.isClosed()) connection.close()
    }
}
