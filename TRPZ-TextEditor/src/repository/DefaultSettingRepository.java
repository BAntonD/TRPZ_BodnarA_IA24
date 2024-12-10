package repository;

import java.sql.*;

public class DefaultSettingRepository {

    private static final String SELECT_DEFAULT_SETTING_QUERY = "SELECT id_Setting FROM DefaultSettings WHERE id = 1";

    // Отримуємо значення id_Setting з таблиці DefaultSetting
    public static int getDefaultSettingId() {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(SELECT_DEFAULT_SETTING_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id_Setting");
            } else {
                throw new RuntimeException("DefaultSetting with id_Setting = 1 not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching from DefaultSetting", e);
        }
    }
}

