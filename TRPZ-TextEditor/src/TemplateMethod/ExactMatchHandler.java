package TemplateMethod;

import javax.swing.*;

public interface ExactMatchHandler {
    void handleExactMatch(String snippetContent, JTextPane textPane, String trigger);
}

