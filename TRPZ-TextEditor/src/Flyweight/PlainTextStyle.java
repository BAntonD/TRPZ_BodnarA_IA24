package Flyweight;

public class PlainTextStyle implements TextStyle {
    @Override
    public void renderText(String text, int x, int y) {
        System.out.println("Рендеримо простий текст: '" + text + "' на позиції (" + x + ", " + y + ")");
    }
}

