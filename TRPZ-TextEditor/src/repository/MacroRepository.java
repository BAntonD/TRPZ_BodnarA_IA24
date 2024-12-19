package repository;

import models.Macro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MacroRepository {
    public void saveMacro(String name, boolean isBold, boolean isItalic, boolean isUnderlined) {
        String sql = "INSERT INTO Macros (name, isBold, isItalic, isUnderlined) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setBoolean(2, isBold);
            pstmt.setBoolean(3, isItalic);
            pstmt.setBoolean(4, isUnderlined);

            pstmt.executeUpdate();
            System.out.println("Макрос збережено у базу даних: " + name);
        } catch (SQLException e) {
            System.err.println("Помилка при збереженні макроса: " + e.getMessage());
        }
    }

    public boolean isMacroExists(boolean isBold, boolean isItalic, boolean isUnderlined) {
        String sql = "SELECT COUNT(*) FROM Macros WHERE isBold = ? AND isItalic = ? AND isUnderlined = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isBold);
            pstmt.setBoolean(2, isItalic);
            pstmt.setBoolean(3, isUnderlined);

            var rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Помилка при перевірці макроса: " + e.getMessage());
            return false;
        }
    }

    public Macro getMacroByStyle(boolean isBold, boolean isItalic, boolean isUnderlined) {
        String sql = "SELECT * FROM Macros WHERE isBold = ? AND isItalic = ? AND isUnderlined = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isBold);
            pstmt.setBoolean(2, isItalic);
            pstmt.setBoolean(3, isUnderlined);

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Macro(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isBold"),
                        rs.getBoolean("isItalic"),
                        rs.getBoolean("isUnderlined")
                );
            }
        } catch (SQLException e) {
            System.err.println("Помилка при отриманні макроса: " + e.getMessage());
        }

        return null;
    }

    public List<Macro> getAllMacros() {
        String sql = "SELECT * FROM Macros";
        List<Macro> macros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            var rs = pstmt.executeQuery();
            while (rs.next()) {
                Macro macro = new Macro(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isBold"),
                        rs.getBoolean("isItalic"),
                        rs.getBoolean("isUnderlined")
                );
                macros.add(macro);
            }
        } catch (SQLException e) {
            System.err.println("Помилка при отриманні макросів: " + e.getMessage());
        }

        return macros;
    }

    public void deleteMacroById(int id) {
        String sql = "DELETE FROM Macros WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Макрос із ID " + id + " видалено.");
        } catch (SQLException e) {
            System.err.println("Помилка при видаленні макроса: " + e.getMessage());
        }
    }
}


