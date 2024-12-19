import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import Flyweight.SyntaxHighlighterProcessor;
import Macro.MacrosService;
import Observer.Observer;
import Observer.Subject;
import TemplateMethod.SnippetProcessor;
import TemplateMethod.SnippetProcessorImpl;
import models.Bookmark;
import repository.SettingsSync;
import navigation.LineNavigator;
import Snippet.SnippetService;
import models.Snippet;

public class Main implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String text = "";

    // Глобальні змінні для стану режимів
    private final AtomicBoolean isDeleteMode = new AtomicBoolean(false); // Стан кнопки Clear
    private final AtomicBoolean isSnippetMode = new AtomicBoolean(true); // Стан Snippet Mode
    private MacrosService macrosService = new MacrosService();

    public static void main(String[] args) {
        new Main(); // Запускаємо редактор
    }

    public Main() {
        SettingsSync.syncSettings();
        LineNavigator lineNavigator = new LineNavigator();

        JFrame frame = new JFrame("TextEditor");
        JTextPane textPane = new JTextPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = createFileMenu(frame, textPane);

        // Додавання меню "File"
        menuBar.add(fileMenu);

        // Додавання панелі переходу по рядках та Snippets
        JPanel linePanel = createLineAndSnippetPanel(textPane, lineNavigator);
        menuBar.add(linePanel);
        linePanel = createSnippetPanel(textPane);
        menuBar.add(linePanel);

        // Додаємо кнопки форматування тексту
        addTextFormattingButtons(menuBar, textPane);
        addMacroButtons(menuBar, textPane);

        // Додавання кнопки Clear та панелі закладок
        JButton clearButton = createClearButton();
        JPanel bookmarkPanel = createBookmarkPanel(textPane, lineNavigator);

        menuBar.add(clearButton); // Додаємо кнопку Clear перед панеллю закладок
        menuBar.add(bookmarkPanel);

        // Налаштування текстової області
        frame.add(new JScrollPane(textPane), BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Додаємо обробку змін тексту та підписників
        setupTextChangeListener(textPane);
        addObserver(new SyntaxHighlighterProcessor(textPane));
        //addObserver(new AutoSave());
    }

    public void addTextFormattingButtons(JMenuBar menuBar, JTextPane textPane) {
        // Панель для кнопок форматування
        JPanel formattingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Створюємо кнопки
        JButton underlineButton = new JButton("Підкреслений");
        JButton italicButton = new JButton("Курсив");
        JButton boldButton = new JButton("Жирний");

        // Встановлюємо розмір кнопок
        underlineButton.setPreferredSize(new Dimension(120, 40));
        italicButton.setPreferredSize(new Dimension(75, 40));
        boldButton.setPreferredSize(new Dimension(85, 40));

        // Додаємо обробники подій для кнопок
        boldButton.addActionListener(e -> toggleBoldStyle(textPane));
        italicButton.addActionListener(e -> toggleItalicStyle(textPane));
        underlineButton.addActionListener(e -> toggleUnderlineStyle(textPane));

        // Додаємо кнопки на панель
        formattingPanel.add(underlineButton);
        formattingPanel.add(italicButton);
        formattingPanel.add(boldButton);

        // Додаємо панель з кнопками на menuBar
        menuBar.add(formattingPanel);
    }

    public static void addMacroButtons(JMenuBar menuBar, JTextPane textPane) {
        JPanel macroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        macroPanel.setBackground(Color.LIGHT_GRAY);

        // Додаємо лейбл
        macroPanel.add(new JLabel("Макроси"));

        // Створюємо кнопки
        JButton macroButton = new JButton("Додати");
        JButton applyMacroButton = new JButton("Використати");
        JButton deleteMacroButton = new JButton("Видалити");

        macroButton.setPreferredSize(new Dimension(80, 40));
        applyMacroButton.setPreferredSize(new Dimension(110, 40));
        deleteMacroButton.setPreferredSize(new Dimension(95, 40));

        MacrosService macrosService = new MacrosService();

        // Обробник для створення макросів
        macroButton.addActionListener(e -> {
            StyledDocument doc = textPane.getStyledDocument();
            int start = textPane.getSelectionStart();
            if (start < textPane.getSelectionEnd()) {
                AttributeSet attributes = doc.getCharacterElement(start).getAttributes();
                boolean isBold = StyleConstants.isBold(attributes);
                boolean isItalic = StyleConstants.isItalic(attributes);
                boolean isUnderlined = StyleConstants.isUnderline(attributes);

                String macroName = JOptionPane.showInputDialog(null, "Введіть ім'я макроса:", "Створення макроса", JOptionPane.PLAIN_MESSAGE);
                if (macroName != null && !macroName.trim().isEmpty()) {
                    macrosService.saveMacro(macroName, isBold, isItalic, isUnderlined);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Виділіть текст для створення макроса.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Обробник для застосування макросів
        applyMacroButton.addActionListener(e -> macrosService.applyMacro(textPane));

        // Обробник для видалення макросів
        deleteMacroButton.addActionListener(e -> macrosService.deleteMacro());

        // Додаємо кнопки на панель
        macroPanel.add(macroButton);
        macroPanel.add(applyMacroButton);
        macroPanel.add(deleteMacroButton);

        menuBar.add(macroPanel);
    }



    private void toggleBoldStyle(JTextPane textPane) {
        toggleTextStyle(textPane, StyleConstants::isBold, StyleConstants::setBold);
    }

    private void toggleItalicStyle(JTextPane textPane) {
        toggleTextStyle(textPane, StyleConstants::isItalic, StyleConstants::setItalic);
    }

    private void toggleUnderlineStyle(JTextPane textPane) {
        toggleTextStyle(textPane, StyleConstants::isUnderline, StyleConstants::setUnderline);
    }

    private void toggleTextStyle(JTextPane textPane,
                                 java.util.function.Function<AttributeSet, Boolean> styleChecker,
                                 java.util.function.BiConsumer<SimpleAttributeSet, Boolean> styleSetter) {
        // Отримуємо поточну виділену частину тексту
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            // Якщо текст не виділений, нічого не робимо
            return;
        }

        // Отримуємо StyledDocument для редагування стилю тексту
        StyledDocument doc = textPane.getStyledDocument();

        // Отримуємо атрибути для поточного виділеного тексту
        AttributeSet attributes = doc.getCharacterElement(start).getAttributes();

        // Перевіряємо, чи текст вже має відповідний стиль
        boolean currentState = styleChecker.apply(attributes);

        // Створюємо SimpleAttributeSet для зміни стилю
        SimpleAttributeSet newAttributes = new SimpleAttributeSet();

        // Змінюємо стиль на протилежний
        styleSetter.accept(newAttributes, !currentState);

        // Застосовуємо стиль до виділеного тексту
        doc.setCharacterAttributes(start, end - start, newAttributes, false);
    }



    private JMenu createFileMenu(JFrame frame, JTextPane textPane) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        openItem.addActionListener(e -> openFile(frame, textPane));
        saveItem.addActionListener(e -> saveFile(frame, textPane));
        exitItem.addActionListener(e -> System.exit(0));

        return fileMenu;
    }

    private JPanel createLineAndSnippetPanel(JTextPane textPane, LineNavigator lineNavigator) {
        JPanel linePanel = new JPanel();
        linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS));

        JLabel goToLineLabel = new JLabel("Go to line:");
        goToLineLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JTextField lineNumberField = new JTextField(3);
        lineNumberField.setPreferredSize(new Dimension(40, 30)); // Висота узгоджена з іншими кнопками
        lineNumberField.setMaximumSize(lineNumberField.getPreferredSize());
        lineNumberField.setAlignmentY(Component.CENTER_ALIGNMENT);

        JButton goToLineButton = new JButton("Go");
        goToLineButton.setPreferredSize(new Dimension(60, 30));
        goToLineButton.setMaximumSize(goToLineButton.getPreferredSize());
        goToLineButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        goToLineButton.addActionListener(e -> {
            String input = lineNumberField.getText();
            try {
                int lineNumber = Integer.parseInt(input);
                lineNavigator.goToLine(lineNumber, textPane);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid line number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        linePanel.add(Box.createRigidArea(new Dimension(5, 0))); // Відступ перед текстом
        linePanel.add(goToLineLabel);
        linePanel.add(Box.createRigidArea(new Dimension(5, 0))); // Відступ між текстом і полем
        linePanel.add(lineNumberField);
        linePanel.add(Box.createRigidArea(new Dimension(5, 0))); // Відступ між полем і кнопкою
        linePanel.add(goToLineButton);
        linePanel.add(Box.createRigidArea(new Dimension(5, 0)));

        setupKeyListener(textPane);
        return linePanel;
    }

    // Панель для роботи зі сніппетами
    private JPanel createSnippetPanel(JTextPane textPane) {
        JPanel snippetPanel = new JPanel();
        snippetPanel.setLayout(new BoxLayout(snippetPanel, BoxLayout.X_AXIS));

        SnippetService snippetService = new SnippetService();

        // Кнопка для увімкнення/вимкнення режиму сніппетів
        JButton snippetToggleButton = new JButton("Snippets");
        snippetToggleButton.setPreferredSize(new Dimension(90, 40));
        snippetToggleButton.setBackground(Color.GREEN);

        // Стан режиму сніппетів
        AtomicBoolean isSnippetMode = new AtomicBoolean(true);
        snippetToggleButton.addActionListener(e -> {
            boolean currentState = isSnippetMode.getAndSet(!isSnippetMode.get());
            snippetToggleButton.setBackground(currentState ? Color.LIGHT_GRAY : Color.GREEN);
            //JOptionPane.showMessageDialog(null, "Snippet mode " + (currentState ? "disabled" : "enabled"), "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Кнопка для налаштувань сніппетів (додавання/видалення)
        JButton snippetSettingsButton = new JButton("Sn. Settings");
        snippetSettingsButton.setPreferredSize(new Dimension(110, 40));
        snippetSettingsButton.setBackground(Color.LIGHT_GRAY);

        // Додавання меню з підпунктами
        JPopupMenu snippetMenu = new JPopupMenu();

        JMenuItem addSnippetItem = new JMenuItem("Add Snippet");
        JMenuItem deleteSnippetItem = new JMenuItem("Delete Snippet");

        snippetMenu.add(addSnippetItem);
        snippetMenu.add(deleteSnippetItem);

        // Додавання функціональності до пунктів меню
        addSnippetItem.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(2, 2));
            JTextField triggerField = new JTextField();
            JTextField contentField = new JTextField();

            inputPanel.add(new JLabel("Trigger:"));
            inputPanel.add(triggerField);
            inputPanel.add(new JLabel("Content:"));
            inputPanel.add(contentField);

            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Snippet", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String trigger = triggerField.getText().trim();
                String content = contentField.getText().trim();

                if (trigger.isEmpty() || content.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both trigger and content are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (snippetService.isTriggerExists(trigger)) {
                    JOptionPane.showMessageDialog(null, "Trigger already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    snippetService.saveSnippet(trigger, content);
                    JOptionPane.showMessageDialog(null, "Snippet added successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        deleteSnippetItem.addActionListener(e -> {
            List<Snippet> snippets = snippetService.getAllSnippets();

            if (snippets.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No snippets available to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Snippet selectedSnippet = (Snippet) JOptionPane.showInputDialog(
                    null,
                    "Select a snippet to delete:",
                    "Delete Snippet",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    snippets.toArray(),
                    null
            );

            if (selectedSnippet != null) {
                snippetService.deleteSnippet(selectedSnippet);
                JOptionPane.showMessageDialog(null, "Snippet deleted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        snippetSettingsButton.addActionListener(e -> snippetMenu.show(snippetSettingsButton, 0, snippetSettingsButton.getHeight()));

        snippetPanel.add(snippetToggleButton);
        snippetPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Проміжок між кнопками
        snippetPanel.add(snippetSettingsButton);

        return snippetPanel;
    }


    private JButton createClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(80, 40));
        clearButton.setBackground(Color.ORANGE);
        clearButton.addActionListener(e -> toggleDeleteMode(clearButton));
        return clearButton;
    }

    private JPanel createBookmarkPanel(JTextPane textPane, LineNavigator lineNavigator) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 9));
        List<Bookmark> bookmarks = new ArrayList<>();
        JButton[] bookmarkButtons = new JButton[9];

        for (int i = 0; i < 9; i++) {
            bookmarks.add(new Bookmark(i));
            final int index = i;
            bookmarkButtons[i] = new JButton(String.valueOf(i + 1));
            bookmarkButtons[i].setPreferredSize(new Dimension(40, 40));
            bookmarkButtons[i].setBackground(Color.GRAY);
            bookmarkButtons[i].addActionListener(e -> handleBookmarkAction(bookmarks.get(index), bookmarkButtons[index], textPane, lineNavigator));
            buttonPanel.add(bookmarkButtons[i]);
        }
        return buttonPanel;
    }

    private void handleBookmarkAction(Bookmark bookmark, JButton button, JTextPane textPane, LineNavigator lineNavigator) {
        if (isDeleteMode.get()) {
            if (bookmark.isState()) {
                bookmark.setState(false);
                bookmark.setLineNumber(0);
                button.setBackground(Color.GRAY);
            }
        } else {
            if (bookmark.isState()) {
                lineNavigator.goToLine(bookmark.getLineNumber(), textPane);
            } else {
                int lineNumber = lineNavigator.getLineNumberAtPosition(textPane.getCaretPosition(), textPane);
                bookmark.setLineNumber(lineNumber);
                bookmark.setState(true);
                button.setBackground(Color.GREEN);
            }
        }
    }

    private void toggleDeleteMode(JButton clearButton) {
        if (isDeleteMode.get()) {
            isDeleteMode.set(false);
            clearButton.setBackground(Color.ORANGE);
        } else {
            isDeleteMode.set(true);
            clearButton.setBackground(Color.RED);
        }
    }

    private void toggleSnippetMode(JButton snippetButton) {
        if (isSnippetMode.get()) {
            isSnippetMode.set(false);
            snippetButton.setBackground(Color.GRAY);
        } else {
            isSnippetMode.set(true);
            snippetButton.setBackground(Color.GREEN);
        }
    }

    private void setupTextChangeListener(JTextPane textPane) {
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                text = textPane.getText();
                notifyObservers(textPane);
            }
        });
    }

    private void setupKeyListener(JTextPane textPane) {
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isSnippetMode.get() && e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    String trigger = getTriggerFromText(textPane);
                    SnippetProcessor snippetProcessor = new SnippetProcessorImpl();
                    snippetProcessor.processSnippet(trigger, textPane);
                }
            }
        });
    }

    private String getTriggerFromText(JTextPane textPane) {
        try {
            int caretPosition = textPane.getCaretPosition();
            String textBeforeCaret = textPane.getText(0, caretPosition);
            String[] words = textBeforeCaret.split("\\s+");
            return words.length > 0 ? words[words.length - 1] : "";
        } catch (BadLocationException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void openFile(JFrame frame, JTextPane textPane) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                textPane.setText(reader.lines().reduce("", (acc, line) -> acc + line + "\n"));
                text = textPane.getText();
                notifyObservers(textPane);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
            }
        }
    }

    private void saveFile(JFrame frame, JTextPane textPane) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write(textPane.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(JTextPane textPane) {
        for (Observer observer : observers) {
            observer.update(textPane);
        }
    }

}
