package Flyweight;
import models.SyntaxHighlighterWord;
import repository.ColorsRepository;
import repository.SyntaxHighlighterWordRepository;
import repository.DatabaseConnection;
import Observer.Observer;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxHighlighterProcessor implements Observer {
    private final JTextPane textPane;
    private final StyledDocument doc;
    private final ColorsRepository colorsRepository;
    private final SyntaxHighlighterWordRepository wordRepository;
    private final StyleFactory styleFactory;

    public SyntaxHighlighterProcessor(JTextPane textPane) {
        this.textPane = textPane;
        this.doc = textPane.getStyledDocument();

        // Ініціалізація репозиторіїв
        this.colorsRepository = new ColorsRepository(DatabaseConnection.connect());
        this.wordRepository = new SyntaxHighlighterWordRepository(DatabaseConnection.connect());

        this.styleFactory = new StyleFactory();
    }

    @Override
    public void update(JTextPane newText) {
        if (newText != null) {
            applyHighlighting(newText); // Основна логіка підсвітки тепер тут
        }
    }

    public void applyHighlighting(JTextPane textPane) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            String text = doc.getText(0, doc.getLength()); // Отримуємо весь текст
            List<SyntaxHighlighterWord> words = wordRepository.findByHighlighterId(1);

            if (words == null || words.isEmpty()) {
                System.err.println("No highlighting words found.");
                return;
            }

            // Спочатку зберігаємо всі стилі та очищуємо лише колір тексту
            for (int i = 0; i < doc.getLength(); i++) {
                AttributeSet attributes = doc.getCharacterElement(i).getAttributes();
                MutableAttributeSet cleanAttributes = new SimpleAttributeSet(attributes);
                StyleConstants.setForeground(cleanAttributes, Color.BLACK); // Чорний за замовчуванням
                doc.setCharacterAttributes(i, 1, cleanAttributes, true);
            }

            // Шукаємо слова для підсвітки
            for (SyntaxHighlighterWord word : words) {
                String searchWord = word.getWord();
                int idColor = word.getIdColor();
                if (searchWord != null && idColor != 0) {
                    String colorCode = colorsRepository.getColorCodeById(idColor);
                    if (colorCode != null) {
                        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(searchWord) + "\\b");
                        Matcher matcher = pattern.matcher(text);

                        while (matcher.find()) {
                            int start = matcher.start();
                            int length = searchWord.length();

                            for (int i = start; i < start + length; i++) {
                                AttributeSet attributes = doc.getCharacterElement(i).getAttributes();
                                MutableAttributeSet newAttributes = new SimpleAttributeSet(attributes);

                                // Отримуємо стиль через фабрику
                                Style style = styleFactory.getStyle(colorCode);
                                Color highlightColor = StyleConstants.getForeground(style);
                                StyleConstants.setForeground(newAttributes, highlightColor);

                                doc.setCharacterAttributes(i, 1, newAttributes, true);
                                System.out.println("[LOG] Застосовано створений стиль для тексту: " + searchWord + " на позиції " + i);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






