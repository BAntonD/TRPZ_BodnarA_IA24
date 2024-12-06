package repository;

import models.Snippet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Snippet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SnippetRepository {

    // Отримуємо контент за тригером і id_SnippetList
    public static String getSnippetByTrigger(String trigger, int idSnippetList) {
        String snippetContent = null;
        String sql = "SELECT content FROM Snippet WHERE trigger = ? AND id_SnippetList = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trigger);
            pstmt.setInt(2, idSnippetList);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                snippetContent = rs.getString("content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return snippetContent; // Повертаємо контент або null, якщо нічого не знайдено
    }

    // Отримуємо список схожих сніппетів
    public static List<Snippet> getSimilarSnippets(String trigger, int idSetting) {
        List<Snippet> similarSnippets = new ArrayList<>();
        String sql = "SELECT Snippet.trigger, Snippet.content " +
                "FROM Snippet " +
                "INNER JOIN Setting ON Snippet.id_SnippetList = Setting.id_SnippetList " +
                "WHERE (Snippet.trigger LIKE ? OR Snippet.trigger LIKE ? OR Snippet.trigger LIKE ?) " +
                "AND Setting.id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trigger + "%");
            pstmt.setString(2, "%" + trigger);
            pstmt.setString(3, "%" + trigger + "%");
            pstmt.setInt(4, idSetting);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String foundTrigger = rs.getString("trigger");
                String content = rs.getString("content");
                similarSnippets.add(new Snippet(foundTrigger, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return similarSnippets;
    }
}


/*public class SnippetRepository {

    // Отримуємо id_Setting з таблиці CurrentSetting
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

    // Отримуємо контент за тригером і id_Setting
    public static String getSnippetByTrigger(String trigger, int idSetting) {
        String snippetContent = null;

        // SQL-запит для отримання id_SnippetList із таблиці Setting
        String sqlGetSnippetListId = "SELECT id_SnippetList FROM Setting WHERE id = ?";

        // SQL-запит для отримання контенту з таблиці Snippet
        String sqlGetSnippet = "SELECT content FROM Snippet WHERE trigger = ? AND id_SnippetList = ?";

        try (Connection conn = DatabaseConnection.connect()) {
            // Отримуємо id_SnippetList
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlGetSnippetListId)) {
                pstmt1.setInt(1, idSetting);
                ResultSet rs1 = pstmt1.executeQuery();
                if (rs1.next()) {
                    int idSnippetList = rs1.getInt("id_SnippetList");

                    // Виконуємо другий запит для отримання контенту
                    try (PreparedStatement pstmt2 = conn.prepareStatement(sqlGetSnippet)) {
                        pstmt2.setString(1, trigger);
                        pstmt2.setInt(2, idSnippetList);
                        ResultSet rs2 = pstmt2.executeQuery();
                        if (rs2.next()) {
                            snippetContent = rs2.getString("content");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return snippetContent; // Повертаємо контент або null, якщо нічого не знайдено
    }

    // Отримуємо список схожих сніппетів
    public static List<Snippet> getSimilarSnippets(String trigger, int idSetting) {
        List<Snippet> similarSnippets = new ArrayList<>();
        String sql = "SELECT Snippet.trigger, Snippet.content " +
                "FROM Snippet " +
                "INNER JOIN Setting ON Snippet.id_SnippetList = Setting.id_SnippetList " +
                "WHERE (Snippet.trigger LIKE ? OR Snippet.trigger LIKE ? OR Snippet.trigger LIKE ?) " +
                "AND Setting.id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trigger + "%");   // Починається з 'trigger'
            pstmt.setString(2, "%" + trigger);   // Закінчується на 'trigger'
            pstmt.setString(3, "%" + trigger + "%"); // Містить 'trigger'
            pstmt.setInt(4, idSetting);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String foundTrigger = rs.getString("trigger");
                String content = rs.getString("content");
                similarSnippets.add(new Snippet(foundTrigger, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return similarSnippets;
    }




}*/
