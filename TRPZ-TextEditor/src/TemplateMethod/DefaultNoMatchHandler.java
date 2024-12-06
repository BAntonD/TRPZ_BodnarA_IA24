package TemplateMethod;

import javax.swing.*;

public class DefaultNoMatchHandler implements NoMatchHandler {
    @Override
    public void handleNoMatch(String message) {
        JOptionPane.showMessageDialog(null, message, "Помилка", JOptionPane.INFORMATION_MESSAGE);
    }
}

