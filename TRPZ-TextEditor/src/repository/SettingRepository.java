package repository;

import java.sql.*;

public class SettingRepository {

    // Отримуємо id_SnippetList за id_Setting
    public static int getSnippetListId(int idSetting) {
        String sql = "SELECT id_SnippetList FROM Setting WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSetting);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_SnippetList");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Повертаємо -1, якщо id_SnippetList не знайдено
    }
}
