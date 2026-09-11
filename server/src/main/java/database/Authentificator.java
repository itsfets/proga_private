package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Authentificator {
    private final Connection connection;

    public Authentificator(Connection connection) {
        this.connection = connection;
    }

    public boolean checkAuth(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password_hash = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("failed checking authorization: " + e.getMessage());
            return false;
        }
    }
}
