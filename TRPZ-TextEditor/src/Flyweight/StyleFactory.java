package Flyweight;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


public class StyleFactory {
    private Map<String, Style> styleCache = new HashMap<>();

    public Style getStyle(String colorCode) {
        System.out.println("[DEBUG] Необхідно стиль для кольору: " + colorCode);
        Style style = styleCache.get(colorCode);
        if (style == null) {
            System.out.println("[DEBUG] Виклик створення нового стилю для кольору: " + colorCode);
            style = createNewStyle(colorCode);
            styleCache.put(colorCode, style);
            System.out.println("[LOG] Створено новий стиль для кольору: " + colorCode);
        }
        return style;
    }

    public Style createNewStyle(String colorCode) {
        Style style = new StyleContext().addStyle(colorCode, null);
        Color highlightColor = parseColor(colorCode);  // Перетворення кольору
        StyleConstants.setForeground(style, highlightColor);
        return style;
    }

    public static Color parseColor(String color) {
        try {
            if (color.startsWith("#")) {
                return Color.decode(color);
            } else {
                return (Color) Color.class.getField(color.toUpperCase()).get(null); // Наприклад, red, blue
            }
        } catch (Exception e) {
            System.err.println("Invalid color format: " + color);
            return Color.BLACK; // За замовчуванням чорний
        }
    }
}

