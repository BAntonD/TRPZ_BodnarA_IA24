import Flyweight.TextCharacter;
import Flyweight.TextStyle;
import Flyweight.TextStyleFactory;

public class Test {
    public static void main(String[] args) {
        TextStyle plainStyle = TextStyleFactory.getTextStyle("plain");
        TextStyle boldStyle = TextStyleFactory.getTextStyle("bold");
        TextStyle italicStyle = TextStyleFactory.getTextStyle("italic");

        TextCharacter a = new TextCharacter('A', plainStyle);
        TextCharacter b = new TextCharacter('B', boldStyle);
        TextCharacter c = new TextCharacter('C', italicStyle);

        a.draw(10, 20); // Рендеримо простий текст
        b.draw(30, 40); // Рендеримо жирний текст
        c.draw(50, 60); // Рендеримо текст курсивом
    }
}

