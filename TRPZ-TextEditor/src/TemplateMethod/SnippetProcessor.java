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
    private final SnippetService snippetService;

    public SnippetProcessor(ExactMatchHandler exactMatchHandler,
                            SimilarMatchesHandler similarMatchesHandler,
                            NoMatchHandler noMatchHandler,
                            SnippetService snippetService) {
        this.exactMatchHandler = exactMatchHandler;
        this.similarMatchesHandler = similarMatchesHandler;
        this.noMatchHandler = noMatchHandler;
        this.snippetService = snippetService; // snippetService не передається у SnippetProcessorImpl
    }

    public void processSnippet(String trigger, JTextPane textPane) {
        if (trigger.isEmpty()) {
            noMatchHandler.handleNoMatch("Text is empty. Please provide a valid trigger.");
        } else {
            String exactMatch = snippetService.getExactSnippet(trigger);
            if (exactMatch != null) {
                exactMatchHandler.handleExactMatch(exactMatch, textPane, trigger);
            } else {
                List<Snippet> similarMatches = snippetService.getSimilarSnippets(trigger);
                if (!similarMatches.isEmpty()) {
                    similarMatchesHandler.handleSimilarMatches(similarMatches);
                } else {
                    noMatchHandler.handleNoMatch("No matching or similar snippets found.");
                }
            }
        }
    }
}

