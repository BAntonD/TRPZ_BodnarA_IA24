package repository;

import java.sql.*;

public class CurrentSettingRepository {

    // Метод для оновлення id_Setting в таблиці CurrentSetting
    public static void updateCurrentSetting(int newSettingId) {
        String sql = "UPDATE CurrentSettings SET id_Setting = ? WHERE id = 1";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newSettingId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Current setting updated successfully.");
            } else {
                System.out.println("No rows updated. Ensure the table has a record with id = 1.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentSettingId() {
        String sql = "SELECT id_Setting FROM CurrentSettings WHERE id = 1";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id_Setting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Повертаємо -1, якщо id_Setting не знайдено
    }
}
