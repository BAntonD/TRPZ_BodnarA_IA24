package repository;

import models.SyntaxHighlighterWord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SyntaxHighlighterWordRepository {
    private final Connection connection;

    public SyntaxHighlighterWordRepository(Connection connection) {
        this.connection = connection;
    }

    // Отримуємо всі слова для підсвітки за id підсвітки
    public List<SyntaxHighlighterWord> findByHighlighterId(int syntaxHighlighterId) {
        List<SyntaxHighlighterWord> words = new ArrayList<>();
        String sql = "SELECT word, id_color FROM SyntaxHighlighterWord WHERE syntax_highlighter_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, syntaxHighlighterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String word = rs.getString("word");
                int idColor = rs.getInt("id_color");
                words.add(new SyntaxHighlighterWord(syntaxHighlighterId, word, idColor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }
}
