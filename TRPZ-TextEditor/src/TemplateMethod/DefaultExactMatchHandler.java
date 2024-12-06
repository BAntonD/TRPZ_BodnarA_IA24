package TemplateMethod;

import javax.swing.JTextPane;

public class DefaultExactMatchHandler implements ExactMatchHandler {
    @Override
    public void handleExactMatch(String snippetContent, JTextPane textPane, String trigger) {
        try {
            // Зберігаємо поточну позицію курсора
            int caretPosition = textPane.getCaretPosition();

            // Отримуємо поточний текст з текстового поля
            String text = textPane.getText();

            // Знайдемо індекс тригера в тексті
            int triggerIndex = text.indexOf(trigger);

            if (triggerIndex != -1) {
                // Якщо тригер знайдено, створимо новий текст з заміною
                StringBuilder newText = new StringBuilder(text);

                // Видаляємо тригер з тексту (по довжині тригера)
                newText.replace(triggerIndex, triggerIndex + trigger.length(), snippetContent);

                // Оновлюємо текст в JTextPane
                textPane.setText(newText.toString());

                // Розбиваємо текст на рядки
                String[] lines = textPane.getText().split("\n");

                // Знаходимо номер рядка, куди вставлено сніппет
                int lineNumber = 0;
                int charCount = 0;
                for (int i = 0; i < lines.length; i++) {
                    charCount += lines[i].length() + 1;  // +1 для нового рядка
                    if (triggerIndex < charCount) {
                        lineNumber = i;
                        break;
                    }
                }

                // Встановлюємо нову позицію курсора
                int position = newText.indexOf(snippetContent) + snippetContent.length();
                textPane.setCaretPosition(position);
                // Можна додатково застосувати log чи консоль для перевірки правильності
                System.out.println("Новий рядок: " + (lineNumber + 1)); // Рядок, куди вставлено
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

