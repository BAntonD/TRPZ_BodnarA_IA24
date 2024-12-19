package TemplateMethod;

public class TextUtils {
    // Метод для отримання тексту конкретного рядка
    public static String getLineText(String text, int lineNumber) {
        String[] lines = text.split("\n");
        if (lineNumber > 0 && lineNumber <= lines.length) {
            return lines[lineNumber - 1];  // Оскільки нумерація рядків починається з 1
        }
        return "";
    }

    // Метод для заміни конкретного рядка в тексті
    public static String replaceLineInText(String text, int lineNumber, String newLine) {
        String[] lines = text.split("\n");
        lines[lineNumber - 1] = newLine;  // Оновлюємо рядок
        return String.join("\n", lines);  // Об'єднуємо рядки назад в текст
    }

    // Метод для пошуку номера рядка за індексом символу
    public static int findLineNumber(String text, int charIndex) {
        String[] lines = text.split("\n");
        int charCount = 0;
        for (int i = 0; i < lines.length; i++) {
            charCount += lines[i].length() + 1; // +1 для нового рядка
            if (charIndex < charCount) {
                return i + 1;  // Повертаємо номер рядка
            }
        }
        return -1;
    }

    // Метод для обчислення кількості символів до конкретного рядка
    public static int getTextUpToLine(String text, int lineNumber) {
        String[] lines = text.split("\n");
        int charCount = 0;
        for (int i = 0; i < lineNumber - 1; i++) {
            charCount += lines[i].length() + 1;  // +1 для нового рядка
        }
        return charCount;
    }
}



