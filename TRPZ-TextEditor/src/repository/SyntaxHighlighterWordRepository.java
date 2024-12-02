package repository;

import models.SyntaxHighlighterWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SyntaxHighlighterWordRepository {
    private final Connection connection;

    public SyntaxHighlighterWordRepository(Connection connection) {
        this.connection = connection;
    }

    public List<SyntaxHighlighterWord> findByHighlighterId(int highlighterId) {
        List<SyntaxHighlighterWord> result = new ArrayList<>();
        String query = "SELECT * FROM SyntaxHighlighterWord WHERE syntax_highlighter_id = ?";
        System.out.println("Executing query: " + query + " with highlighterId = " + highlighterId); // Логування перед запитом
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, highlighterId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Query executed successfully, processing results...");
            while (rs.next()) {
                result.add(new SyntaxHighlighterWord(rs.getInt("id"), rs.getInt("syntax_highlighter_id"), rs.getString("word"), rs.getString("color")));
            }
            System.out.println("Found " + result.size() + " SyntaxHighlighterWord(s)."); // Логування результатів
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public void save(SyntaxHighlighterWord word) throws SQLException {
        String query = "INSERT INTO SyntaxHighlighterWord (syntax_highlighter_id, word, color) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, word.getSyntaxHighlighterId());
            pstmt.setString(2, word.getWord());
            pstmt.setString(3, word.getColor());
            pstmt.executeUpdate();
        }
    }
}

