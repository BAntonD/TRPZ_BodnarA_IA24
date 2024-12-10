package Flyweight;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import repository.SyntaxHighlighterWordRepository;
import repository.ColorsRepository;
import models.SyntaxHighlighterWord;

public class DatabaseSyntaxHighlighter extends SyntaxHighlighterProcessorTemplate {
    private final SyntaxHighlighterWordRepository wordRepository;
    private final ColorsRepository colorsRepository;
    private final StyleFactory styleFactory;

    public DatabaseSyntaxHighlighter(JTextPane textPane,
                                     SyntaxHighlighterWordRepository wordRepository,
                                     ColorsRepository colorsRepository) {
        super(textPane, colorsRepository, wordRepository); // Тепер передаємо всі три параметри в super
        this.wordRepository = wordRepository;
        this.colorsRepository = colorsRepository;
        this.styleFactory = new StyleFactory(); // Ініціалізуємо фабрику стилів
    }

    @Override
    protected List<SyntaxHighlighterWord> getHighlightingWords() {
        return wordRepository.findByHighlighterId(1);  // Отримуємо слова для підсвітки
    }

    @Override
    protected void applyStyleToWord(String line, SyntaxHighlighterWord word, int offset) {
        try {
            // Отримуємо код кольору для цього слова
            String colorCode = colorsRepository.getColorCodeById(word.getIdColor());

            if (colorCode != null) {
                // Створюємо стиль для цього кольору
                Color highlightColor = StyleFactory.parseColor(colorCode);  // Використовуємо StyleFactory для парсингу кольору
                Style style = styleFactory.getStyle(colorCode);  // Створюємо стиль для цього кольору

                // Перевірка, чи стиль був створений коректно
                if (style != null) {
                    doc.setCharacterAttributes(offset, word.getWord().length(), style, true);
                } else {
                    System.err.println("Style not created for word: " + word.getWord());
                }
            } else {
                System.err.println("Color code not found for word: " + word.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
