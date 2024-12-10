package Flyweight;

public class BoldTextStyle implements TextStyle {
    @Override
    public void renderText(String text, int x, int y) {
        System.out.println("Рендеримо жирний текст: '" + text + "' на позиції (" + x + ", " + y + ")");
    }
}

