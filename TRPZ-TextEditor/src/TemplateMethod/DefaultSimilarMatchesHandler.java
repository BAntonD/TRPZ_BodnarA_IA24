package TemplateMethod;

import models.Snippet;
import javax.swing.*;
import java.util.List;

public class DefaultSimilarMatchesHandler implements SimilarMatchesHandler {
    @Override
    public void handleSimilarMatches(List<Snippet> similarMatches) {
        if (similarMatches.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Немає схожих сніппетів.",
                    "Інформація", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Знайдені схожі сніппети:\n");
            for (Snippet snippet : similarMatches) {
                message.append("Тригер: ").append(snippet.getTrigger()).append("\n");
                message.append("Контент: ").append(snippet.getContent()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(),
                    "Схожі сніппети", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

