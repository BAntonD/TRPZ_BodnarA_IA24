import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Observer.Observer;
import Observer.SyntaxHighlighterService;
import Observer.Subject;
import Observer.AutoSave;
import TemplateMethod.SnippetProcessor;
import TemplateMethod.SnippetProcessorImpl;
import repository.SettingsSync;

public class Main implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String text = "";

    public static void main(String[] args) {
        new Main(); // Запускаємо редактор
    }

    public Main() {

        SettingsSync.syncSettings();
        // Створення основного вікна
        JFrame frame = new JFrame("TextEditor");
        JTextPane textPane = new JTextPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Додавання елементів меню
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Обробка змін у текстовій області
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                text = textPane.getText();
                notifyObservers(); // Сповіщаємо всіх підписників при зміні тексту
            }
        });

        // Обробка дій меню
        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textPane.setText(reader.lines().reduce("", (acc, line) -> acc + line + "\n"));
                    text = textPane.getText();
                    notifyObservers(); // Сповіщаємо підписників про новий текст
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
                }
            }
        });

        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textPane.getText());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }
            }
        });

        exitItem.addActionListener(e -> System.exit(0));

        // Налаштування текстової області
        frame.add(new JScrollPane(textPane), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Додаємо обробку гарячих клавіш
        setupKeyListener(textPane);



        // Додаємо підписників
        addObserver(new SyntaxHighlighterService(textPane));
        addObserver(new AutoSave());

    }

    private void setupKeyListener(JTextPane textPane) {
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    System.out.println("(1)Ctrl pressed"); // Логування натискання Ctrl
                    String trigger = getTriggerFromText(textPane); // Отримуємо тригер
                    System.out.println("(2)Trigger to process: " + trigger); // Логування тригера
                    SnippetProcessor snippetProcessor = new SnippetProcessorImpl();
                    snippetProcessor.processSnippet(trigger, textPane); // Передаємо тригер на обробку
                }
            }
        });
    }

    private String getTriggerFromText(JTextPane textPane) {
        try {
            // Отримуємо поточну позицію курсора в JTextPane
            int caretPosition = textPane.getCaretPosition();
            // Отримуємо текст з початку до позиції курсора
            String textBeforeCaret = textPane.getText(0, caretPosition);
            // Розбиваємо текст на слова, використовуючи пробіли та інші роздільники
            String[] words = textBeforeCaret.split("\\s+");
            // Якщо є слова перед курсором, останнє слово буде тригером
            String trigger = words.length > 0 ? words[words.length - 1] : "";
            System.out.println("(3)Тригер : " + trigger); // Логування тригера
            return trigger;
        } catch (BadLocationException e) {
            e.printStackTrace();
            return ""; // Якщо сталася помилка, повертаємо порожній тригер
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
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(text);
        }
    }
}