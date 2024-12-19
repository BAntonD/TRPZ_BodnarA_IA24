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

    public void saveSnippet(String trigger, String content, int snippetListId) {
        String sql = "INSERT INTO Snippet (trigger, content, id_SnippetList) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trigger);
            pstmt.setString(2, content);
            pstmt.setInt(3, snippetListId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTriggerExists(String trigger, int snippetListId) {
        String sql = "SELECT 1 FROM Snippet WHERE trigger = ? AND id_SnippetList = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trigger);
            pstmt.setInt(2, snippetListId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Snippet> getAllSnippets(int snippetListId) {
        List<Snippet> snippets = new ArrayList<>();
        String sql = "SELECT trigger, content FROM Snippet WHERE id_SnippetList = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, snippetListId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                snippets.add(new Snippet(rs.getString("trigger"), rs.getString("content")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return snippets;
    }

    public void deleteSnippet(Snippet snippet, int snippetListId) {
        String sql = "DELETE FROM Snippet WHERE trigger = ? AND id_SnippetList = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, snippet.getTrigger());
            pstmt.setInt(2, snippetListId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



