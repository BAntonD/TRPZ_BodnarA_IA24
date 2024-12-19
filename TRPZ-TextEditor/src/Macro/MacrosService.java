package Macro;

import repository.MacroRepository;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import models.Macro;
import java.util.List;

public class MacrosService {
    private final MacroRepository macroRepository = new MacroRepository();

    public void saveMacro(String name, boolean isBold, boolean isItalic, boolean isUnderlined) {
        if (macroRepository.isMacroExists(isBold, isItalic, isUnderlined)) {
            Macro existingMacro = macroRepository.getMacroByStyle(isBold, isItalic, isUnderlined);
            JOptionPane.showMessageDialog(null, "Макрос із такими стилями вже існує: " + existingMacro.getName(), "Помилка", JOptionPane.ERROR_MESSAGE);
        } else {
            macroRepository.saveMacro(name, isBold, isItalic, isUnderlined);
        }
    }

    public List<Macro> getAllMacros() {
        return macroRepository.getAllMacros();
    }

    public void applyMacro(JTextPane textPane) {
        List<Macro> macros = getAllMacros();

        if (macros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Немає доступних макросів для застосування.", "Повідомлення", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Macro selectedMacro = (Macro) JOptionPane.showInputDialog(
                null,
                "Оберіть макрос для застосування:",
                "Застосування макроса",
                JOptionPane.PLAIN_MESSAGE,
                null,
                macros.toArray(),
                null
        );

        if (selectedMacro != null) {
            applyMacroToTextPane(textPane, selectedMacro);
            JOptionPane.showMessageDialog(null, "Макрос застосовано: " + selectedMacro.getName(), "Повідомлення", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void applyMacroToTextPane(JTextPane textPane, Macro macro) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();

        StyleConstants.setBold(attributes, macro.isBold());
        StyleConstants.setItalic(attributes, macro.isItalic());
        StyleConstants.setUnderline(attributes, macro.isUnderlined());

        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start != end) {
            StyledDocument doc = textPane.getStyledDocument();
            doc.setCharacterAttributes(start, end - start, attributes, false);
        } else {
            JOptionPane.showMessageDialog(null, "Виділіть текст для застосування макроса.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteMacro() {
        List<Macro> macros = getAllMacros();

        if (macros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Немає доступних макросів для видалення.", "Повідомлення", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] macroNames = macros.stream()
                .map(Macro::getName)
                .toArray(String[]::new);

        String selectedName = (String) JOptionPane.showInputDialog(
                null,
                "Оберіть макрос для видалення:",
                "Видалення макроса",
                JOptionPane.PLAIN_MESSAGE,
                null,
                macroNames,
                null
        );

        if (selectedName != null) {
            Macro selectedMacro = macros.stream()
                    .filter(macro -> macro.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (selectedMacro != null) {
                macroRepository.deleteMacroById(selectedMacro.getId());
                JOptionPane.showMessageDialog(null, "Макрос видалено: " + selectedMacro.getName(), "Повідомлення", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}




