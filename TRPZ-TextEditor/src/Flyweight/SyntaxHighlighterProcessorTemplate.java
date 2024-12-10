package Flyweight;

import models.SyntaxHighlighterWord;
import repository.ColorsRepository;
import repository.SyntaxHighlighterWordRepository;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.*;
import Flyweight.StyleFactory;

// Абстрактний клас для обробки підсвітки
public abstract class SyntaxHighlighterProcessorTemplate {
    protected final JTextPane textPane;
    protected final StyledDocument doc;
    protected final ColorsRepository colorsRepository;
    protected final SyntaxHighlighterWordRepository wordRepository;
    protected final StyleFactory styleFactory;

    public SyntaxHighlighterProcessorTemplate(JTextPane textPane, ColorsRepository colorsRepository, SyntaxHighlighterWordRepository wordRepository) {
        this.textPane = textPane;
        this.doc = textPane.getStyledDocument();
        this.colorsRepository = colorsRepository;
        this.styleFactory = new StyleFactory();
        this.wordRepository = wordRepository;
    }

    protected abstract List<SyntaxHighlighterWord> getHighlightingWords();

    protected void applyStyleToWord(String line, SyntaxHighlighterWord word, int offset) {
        try {
            String colorCode = colorsRepository.getColorCodeById(word.getIdColor());
            if (colorCode != null) {
                Color highlightColor = StyleFactory.parseColor(colorCode);
                if (highlightColor != null) {
                    Style style = styleFactory.getStyle(colorCode);
                    if (style == null) {
                        style = styleFactory.createNewStyle(colorCode);
                    }
                    doc.setCharacterAttributes(offset, word.getWord().length(), style, true);
                } else {
                    System.err.println("Invalid color code for word: " + word.getWord());
                }
            } else {
                System.err.println("Color code is null for word: " + word.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void applyHighlighting() {
        try {
            int cursorPosition = textPane.getCaretPosition();
            String text = textPane.getText();
            String[] lines = text.split("\n");
            int offset = 0;
            Style defaultStyle = doc.getStyle("Default");
            if (defaultStyle == null) {
                defaultStyle = doc.addStyle("Default", null);
                StyleConstants.setForeground(defaultStyle, Color.BLACK);
            }

            doc.setCharacterAttributes(0, text.length(), defaultStyle, true);
            List<SyntaxHighlighterWord> words = wordRepository.findByHighlighterId(1);
            if (words == null || words.isEmpty()) {
                System.err.println("No highlighting words found.");
                return;
            }

            for (String line : lines) {
                doc.setCharacterAttributes(offset, line.length(), defaultStyle, true);
                for (SyntaxHighlighterWord word : words) {
                    String searchWord = word.getWord();
                    int idColor = word.getIdColor();

                    if (searchWord != null && idColor != 0) {
                        String colorCode = colorsRepository.getColorCodeById(idColor);
                        if (colorCode != null) {
                            Color highlightColor = StyleFactory.parseColor(colorCode);
                            Style style = doc.getStyle(searchWord);
                            if (style == null) {
                                style = doc.addStyle(searchWord, null);
                                StyleConstants.setForeground(style, highlightColor);
                            }

                            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(searchWord) + "\\b");
                            Matcher matcher = pattern.matcher(line);

                            while (matcher.find()) {
                                doc.setCharacterAttributes(offset + matcher.start(), searchWord.length(), style, true);
                            }
                        }
                    }
                }
                offset += line.length();
            }
            textPane.setCaretPosition(cursorPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

