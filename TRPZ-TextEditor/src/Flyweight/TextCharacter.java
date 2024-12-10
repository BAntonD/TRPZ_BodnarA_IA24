package Flyweight;

public class TextCharacter {
    private char character; // Символ тексту
    private TextStyle textStyle; // Стиль тексту (Flyweight)

    public TextCharacter(char character, TextStyle textStyle) {
        this.character = character;
        this.textStyle = textStyle;
    }

    public void draw(int x, int y) {
        textStyle.renderText(String.valueOf(character), x, y); // Використовуємо стиль для рендерингу
    }
}

