package TemplateMethod;

import javax.swing.JTextPane;

public class DefaultExactMatchHandler implements ExactMatchHandler {
    @Override
    public void handleExactMatch(String snippetContent, JTextPane textPane, String trigger) {
        try {
            String text = textPane.getText();
            int cursorPosition = textPane.getCaretPosition();  // Отримуємо поточну позицію курсора

            // Знаходимо рядок, в якому знаходиться курсор
            int lineNumber = TextUtils.findLineNumber(text, cursorPosition);  // Знаходимо номер рядка
            String lineText = TextUtils.getLineText(text, lineNumber);  // Отримуємо текст цього рядка
            int triggerIndex = lineText.lastIndexOf(trigger);  // Шукаємо тригер в рядку

            if (triggerIndex != -1) {
                // Розділяємо рядок на частини до тригера та після тригера
                String beforeTrigger = lineText.substring(0, triggerIndex);
                String afterTrigger = lineText.substring(triggerIndex + trigger.length());

                // Заміщаємо тригер на сніппет
                String modifiedLine = beforeTrigger + snippetContent + afterTrigger;

                // Тепер об'єднуємо змінений рядок з рештою тексту
                String modifiedText = TextUtils.replaceLineInText(text, lineNumber, modifiedLine);
                textPane.setText(modifiedText);  // Вставляємо новий текст в JTextPane

                // Обчислюємо нову позицію курсора: після вставленого сніппета
                int newCaretPosition = beforeTrigger.length() + snippetContent.length();
                int offset = TextUtils.getTextUpToLine(text, lineNumber); // Обчислюємо кількість символів до цього рядка
                textPane.setCaretPosition(newCaretPosition + offset);  // Встановлюємо курсор в нову позицію

            } else {
                // Якщо тригер не знайдений
                System.out.println("Тригер не знайдений перед курсором.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









