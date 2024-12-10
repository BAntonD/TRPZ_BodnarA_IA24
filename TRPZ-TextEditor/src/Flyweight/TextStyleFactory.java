package Flyweight;

import java.util.HashMap;
import java.util.Map;

public class TextStyleFactory {
    private static final Map<String, TextStyle> styles = new HashMap<>();

    public static TextStyle getTextStyle(String style) {
        if (!styles.containsKey(style)) {
            switch (style.toLowerCase()) {
                case "plain":
                    styles.put("plain", new PlainTextStyle());
                    break;
                case "bold":
                    styles.put("bold", new BoldTextStyle());
                    break;
                case "italic":
                    styles.put("italic", new ItalicTextStyle());
                    break;
            }
        }
        return styles.get(style);
    }
}

