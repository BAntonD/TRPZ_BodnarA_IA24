package Flyweight;

public class ItalicTextStyle implements TextStyle {
    @Override
    public void renderText(String text, int x, int y) {
        System.out.println("Рендеримо текст курсивом: '" + text + "' на позиції (" + x + ", " + y + ")");
    }
}

