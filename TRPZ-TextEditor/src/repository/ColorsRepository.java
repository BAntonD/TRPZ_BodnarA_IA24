package repository;

import java.awt.Color;
import java.sql.*;

public class ColorsRepository {
    private final Connection connection;

    public ColorsRepository(Connection connection) {
        this.connection = connection;
    }

    // Отримуємо колір за ID
    /*
    public Color getColorById(int idColor) {
        String sql = "SELECT code FROM Colors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idColor);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String colorCode = rs.getString("code");
                return Color.decode(colorCode);  // перетворюємо hex-код в об'єкт Color
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

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
