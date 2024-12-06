package repository;

import models.SyntaxHighlighter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SyntaxHighlighterRepository {
    private final Connection connection;

    public SyntaxHighlighterRepository(Connection connection) {
        this.connection = connection;
    }

    public List<SyntaxHighlighter> findAll() throws SQLException {
        List<SyntaxHighlighter> syntaxHighlighters = new ArrayList<>();
        String query = "SELECT id, name FROM SyntaxHighlighter";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                syntaxHighlighters.add(new SyntaxHighlighter(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }
        return syntaxHighlighters;
    }

    public void save(SyntaxHighlighter syntaxHighlighter) throws SQLException {
        String query = "INSERT INTO SyntaxHighlighter (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, syntaxHighlighter.getName());
            pstmt.executeUpdate();
        }
    }
}


