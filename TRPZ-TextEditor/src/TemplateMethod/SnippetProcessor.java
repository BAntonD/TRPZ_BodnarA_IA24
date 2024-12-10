package TemplateMethod;

import models.Snippet;
import repository.CurrentSettingRepository;
import repository.SettingRepository;
import repository.SnippetRepository;

import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public abstract class SnippetProcessor {

    private final ExactMatchHandler exactMatchHandler;
    private final SimilarMatchesHandler similarMatchesHandler;
    private final NoMatchHandler noMatchHandler;

    public SnippetProcessor(ExactMatchHandler exactMatchHandler,
                            SimilarMatchesHandler similarMatchesHandler,
                            NoMatchHandler noMatchHandler) {
        this.exactMatchHandler = exactMatchHandler;
        this.similarMatchesHandler = similarMatchesHandler;
        this.noMatchHandler = noMatchHandler;
    }

    public void processSnippet(String trigger, JTextPane textPane) {
        if (trigger.isEmpty()) {
            noMatchHandler.handleNoMatch("Text is empty. Please provide a valid trigger.");
        } else {
            int idSetting = CurrentSettingRepository.getCurrentSettingId(); //отримуємо CurrentSettingId з окремого класу
            int idSnippedList = SettingRepository.getSnippetListId(idSetting); //отримуємо SnippedListId з окремого класу
            String exactMatch = SnippetRepository.getSnippetByTrigger(trigger, idSnippedList );// тут вже йде точной пошук
            if (exactMatch != null) {
                exactMatchHandler.handleExactMatch(exactMatch, textPane, trigger);
            } else {
                List<Snippet> similarMatches = SnippetRepository.getSimilarSnippets(trigger, idSetting); // а потім вже це якщо там не буде точного збігу
                if (!similarMatches.isEmpty()) {
                    similarMatchesHandler.handleSimilarMatches(similarMatches);
                } else {
                    noMatchHandler.handleNoMatch("No matching or similar snippets found.");
                }
            }
        }
    }

}
