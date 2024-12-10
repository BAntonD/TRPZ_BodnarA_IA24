package Observer;
/*
import models.SyntaxHighlighterWord;
import repository.SyntaxHighlighterWordRepository;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import repository.DatabaseConnection;


public class SyntaxHighlighterService implements Observer {
    private final JTextPane textPane;
    private final StyledDocument doc;
    private final SyntaxHighlighterWordRepository wordRepository;

    public SyntaxHighlighterService(JTextPane textPane) {
        this.textPane = textPane;
        this.doc = textPane.getStyledDocument();
        this.wordRepository = new SyntaxHighlighterWordRepository(DatabaseConnection.connect());

        System.out.println("Connected to word repository.");

        Style style = doc.getStyle("Keyword");
        if (style == null) {
            style = doc.addStyle("Keyword", null);
            StyleConstants.setForeground(style, Color.BLUE); // Встановлюємо колір для ключових слів
        }

    }

    @Override
    public void update(String data) {
        SwingUtilities.invokeLater(this::applyHighlighting);
    }

    private void applyHighlighting() {
        try {
            // Зберігаємо поточну позицію курсора
            int cursorPosition = textPane.getCaretPosition();

            // Зчитуємо весь текст
            String text = textPane.getText();

            // Розбиваємо текст на рядки
            String[] lines = text.split("\n");

            // Позиція для кожного символа в тексті
            int offset = 0;

            // Перевірка стилю "Default"
            Style defaultStyle = doc.getStyle("Default");
            if (defaultStyle == null) {
                defaultStyle = doc.addStyle("Default", null);
                StyleConstants.setForeground(defaultStyle, Color.BLACK);
            }

            // Скидаємо стилі для всього тексту
            doc.setCharacterAttributes(0, text.length(), defaultStyle, true);

            // Отримуємо всі слова для підсвітки з бази
            List<SyntaxHighlighterWord> words = wordRepository.findByHighlighterId(1);

            // Обробляємо кожен рядок
            for (String line : lines) {
                // Скидаємо стилі для поточного рядка
                doc.setCharacterAttributes(offset, line.length(), defaultStyle, true);

                // Підсвічуємо кожне знайдене слово
                for (SyntaxHighlighterWord word : words) {
                    String searchWord = word.getWord();
                    String color = word.getColor();

                    if (searchWord != null && color != null) {
                        int index = 0;
                        // Використовуємо регулярний вираз для пошуку повних слів
                        String regex = "\\b" + Pattern.quote(searchWord) + "\\b";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(line);

                        while (matcher.find()) {
                            try {
                                // Перевіряємо і застосовуємо колір
                                Color highlightColor = parseColor(color);
                                if (highlightColor != null) {
                                    Style style = doc.getStyle(searchWord);
                                    if (style == null) {
                                        style = doc.addStyle(searchWord, null);
                                        StyleConstants.setForeground(style, highlightColor);
                                    }
                                    doc.setCharacterAttributes(offset + matcher.start(), searchWord.length(), style, true);
                                }
                            } catch (Exception e) {
                                System.err.println("Invalid color format for word: " + searchWord);
                            }
                        }
                    }
                }

                // Оновлюємо зміщення для наступного рядка
                offset += line.length(); // Оновлюємо зміщення без додавання 1 для символа нового рядка
            }

            // Відновлюємо позицію курсора
            textPane.setCaretPosition(cursorPosition);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Color parseColor(String color) {
        try {
            if (color.startsWith("#")) {
                return Color.decode(color);
            } else {
                Field field = Color.class.getField(color.toLowerCase());
                return (Color) field.get(null);
            }
        } catch (Exception e) {
            System.err.println("Invalid color format: " + color);
            return null;
        }
    }
}




    /* Підсвідка до 8 лаби
    @Override
    public void update(String data) {
        SwingUtilities.invokeLater(this::applyHighlighting);
    }

     Підсвідка до 8 лаби
    private void applyHighlighting() {
        try {
            // Зберігаємо поточну позицію курсора
            int cursorPosition = textPane.getCaretPosition();

            // Зчитуємо весь текст
            String text = textPane.getText();

            // Розбиваємо текст на рядки
            String[] lines = text.split("\n");

            // Позиція для кожного символа в тексті
            int offset = 0;

            // Перевірка стилю "Default"
            Style defaultStyle = doc.getStyle("Default");
            if (defaultStyle == null) {
                defaultStyle = doc.addStyle("Default", null);
                StyleConstants.setForeground(defaultStyle, Color.BLACK);
            }

            // Скидаємо стилі для всього тексту
            doc.setCharacterAttributes(0, text.length(), defaultStyle, true);

            // Отримуємо всі слова для підсвітки з бази
            List<SyntaxHighlighterWord> words = wordRepository.findByHighlighterId(1);

            // Обробляємо кожен рядок
            for (String line : lines) {
                // Скидаємо стилі для поточного рядка
                doc.setCharacterAttributes(offset, line.length(), defaultStyle, true);

                // Підсвічуємо кожне знайдене слово
                for (SyntaxHighlighterWord word : words) {
                    String searchWord = word.getWord();
                    String color = word.getColor();

                    if (searchWord != null && color != null) {
                        int index = 0;
                        // Використовуємо регулярний вираз для пошуку повних слів
                        String regex = "\\b" + Pattern.quote(searchWord) + "\\b";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(line);

                        while (matcher.find()) {
                            try {
                                // Перевіряємо і застосовуємо колір
                                Color highlightColor = parseColor(color);
                                if (highlightColor != null) {
                                    Style style = doc.getStyle(searchWord);
                                    if (style == null) {
                                        style = doc.addStyle(searchWord, null);
                                        StyleConstants.setForeground(style, highlightColor);
                                    }
                                    doc.setCharacterAttributes(offset + matcher.start(), searchWord.length(), style, true);
                                }
                            } catch (Exception e) {
                                System.err.println("Invalid color format for word: " + searchWord);
                            }
                        }
                    }
                }

                // Оновлюємо зміщення для наступного рядка
                offset += line.length(); // Оновлюємо зміщення без додавання 1 для символа нового рядка
            }

            // Відновлюємо позицію курсора
            textPane.setCaretPosition(cursorPosition);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private Color parseColor(String color) {
        try {
            if (color.startsWith("#")) {
                return Color.decode(color);
            } else {
                Field field = Color.class.getField(color.toLowerCase());
                return (Color) field.get(null);
            }
        } catch (Exception e) {
            System.err.println("Invalid color format: " + color);
            return null;
        }
    }



}



    public SyntaxHighlighterService(JTextPane textPane) {
        this.textPane = textPane;
        this.doc = textPane.getStyledDocument();

        // Налаштування стилю для ключових слів
        Style keywordStyle = doc.addStyle("Keyword", null);
        StyleConstants.setForeground(keywordStyle, Color.BLUE); // Синій текст
        StyleConstants.setBold(keywordStyle, true);

        // Налаштування стилю для звичайного тексту
        Style defaultStyle = doc.addStyle("Default", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK); // Чорний текст
    }




    /*

    @Override
    public void update(String data) {
        SwingUtilities.invokeLater(this::applyHighlighting);
    }

        private static final Set<String> KEYWORDS = new HashSet<>();
    static {
        // Список ключових слів для підсвічування
        KEYWORDS.add("public");
        KEYWORDS.add("class");
        KEYWORDS.add("static");
        KEYWORDS.add("void");
        KEYWORDS.add("int");
        KEYWORDS.add("String");
        KEYWORDS.add("System");
        KEYWORDS.add("out");
        KEYWORDS.add("println");
    }

    private void applyHighlighting() {
        try {
            // Зберігаємо поточну позицію курсора
            int cursorPosition = textPane.getCaretPosition();

            // Зчитуємо весь текст
            String text = textPane.getText();

            // Розбиваємо текст на рядки
            String[] lines = text.split("\n");

            // Позиція для кожного символа в тексті
            int offset = 0;

            // Обробляємо кожен рядок
            for (String line : lines) {
                // Скидаємо стилі для поточного рядка
                doc.setCharacterAttributes(offset, line.length(), doc.getStyle("Default"), true);

                // Шукаємо ключові слова в рядку
                Pattern pattern = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");
                Matcher matcher = pattern.matcher(line);

                // Підсвічуємо кожне ключове слово
                while (matcher.find()) {
                    int start = offset + matcher.start(); // Початок слова
                    int end = offset + matcher.end();     // Кінець слова

                    // Застосовуємо стиль для знайденого ключового слова
                    doc.setCharacterAttributes(start, end - start, doc.getStyle("Keyword"), true);
                }

                // Оновлюємо зміщення для наступного рядка
                offset += line.length(); // Оновлюємо зміщення без додавання 1 для символа нового рядка
            }

            // Відновлюємо позицію курсора
            textPane.setCaretPosition(cursorPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/






