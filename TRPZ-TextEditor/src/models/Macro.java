package models;

public class Macro {
    private int id;
    private String name;
    private String type;
    private int fontSize;
    private boolean boldFont;
    private boolean italics;
    private boolean underlinedText;
    private String textColor;
    private int lineSpacing;
    private int paragraphSpacing;
    private String style;
    private String textAlignment;
    private String hotKeys;
    private boolean allFile;

    public Macro(int id, String name, String type, int fontSize, boolean boldFont, boolean italics, boolean underlinedText, String textColor,
                 int lineSpacing, int paragraphSpacing, String style, String textAlignment, String hotKeys, boolean allFile) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fontSize = fontSize;
        this.boldFont = boldFont;
        this.italics = italics;
        this.underlinedText = underlinedText;
        this.textColor = textColor;
        this.lineSpacing = lineSpacing;
        this.paragraphSpacing = paragraphSpacing;
        this.style = style;
        this.textAlignment = textAlignment;
        this.hotKeys = hotKeys;
        this.allFile = allFile;
    }

    // Гетери та сетери (не включені для стислості)
}


