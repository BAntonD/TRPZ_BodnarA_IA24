package Flyweight;

import Flyweight.DatabaseSyntaxHighlighter;
import Flyweight.SyntaxHighlighterProcessorTemplate;
import repository.ColorsRepository;
import repository.SyntaxHighlighterWordRepository;
import repository.DatabaseConnection;
import Observer.Observer;

import javax.swing.*;
import java.util.Objects;

public class SyntaxHighlighterProcessor implements Observer {
    private final JTextPane textPane;
    private final SyntaxHighlighterProcessorTemplate highlighterProcessor;

    public SyntaxHighlighterProcessor(JTextPane textPane) {
        this.textPane = textPane;

        // Передаємо з'єднання з базою даних у репозиторій
        SyntaxHighlighterWordRepository wordRepository = new SyntaxHighlighterWordRepository(DatabaseConnection.connect());
        ColorsRepository colorsRepository = new ColorsRepository(DatabaseConnection.connect());
        // Ініціалізуємо процесор підсвітки
        this.highlighterProcessor = new DatabaseSyntaxHighlighter(textPane, wordRepository, colorsRepository);

    }

    @Override
    public void update(String newText) {
        if (Objects.nonNull(newText)) {
            highlighterProcessor.applyHighlighting(); // Викликаємо процес підсвітки
        }
    }
}
