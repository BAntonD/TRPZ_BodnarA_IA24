package navigation;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.awt.*;

public class LineNavigator {

    public int getLineNumberAtPosition(int position, JTextPane textPane) {
        Element root = textPane.getDocument().getDefaultRootElement();
        return root.getElementIndex(position) + 1; // Номер рядка (1-based)
    }

    // Метод для переходу до рядка з встановленням курсора на початок рядка
    public void goToLine(int lineNumber, JTextPane textPane) {
        try {
            // Отримуємо елементи документа
            Element root = textPane.getDocument().getDefaultRootElement();

            // Перевіряємо, чи рядок знаходиться в межах тексту
            if (lineNumber < 1 || lineNumber > root.getElementCount()) {
                JOptionPane.showMessageDialog(null, "Invalid line number: " + lineNumber);
                return;
            }

            // Отримуємо елемент рядка
            Element lineElement = root.getElement(lineNumber - 1);

            // Визначаємо початок рядка
            int startOffset = lineElement.getStartOffset();

            // Встановлюємо курсор на початок рядка
            textPane.setCaretPosition(startOffset);

            // Прокручуємо до видимого місця, використовуючи SwingUtilities.invokeLater для синхронізації
            SwingUtilities.invokeLater(() -> {
                try {
                    Rectangle viewRect = textPane.modelToView2D(startOffset).getBounds();
                    textPane.scrollRectToVisible(viewRect);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            });

            // Встановлюємо фокус на JTextPane
            textPane.requestFocusInWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
