package repository;

import java.awt.Color;
import java.sql.*;

public class ColorsRepository {
    private final Connection connection;

    public ColorsRepository(Connection connection) {
        this.connection = connection;
    }

    public String getColorCodeById(int idColor) throws SQLException {
        String query = "SELECT code FROM Colors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idColor);  // Встановлюємо id_color
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("code");  // Повертаємо code кольору
            } else {
                throw new SQLException("Color not found for id: " + idColor);
            }
        }
    }
}
