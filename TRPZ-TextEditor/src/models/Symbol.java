package models;

public class Symbol {
    private char character;  // Символ
    private boolean isBold;  // Жирний
    private boolean isItalic;  // Курсив
    private boolean isUnderlined;  // Підкреслений

    public Symbol(char character, boolean isBold, boolean isItalic, boolean isUnderlined) {
        this.character = character;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderlined = isUnderlined;
    }

    public char getCharacter() {
        return character;
    }

    public boolean isBold() {
        return isBold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public boolean isUnderlined() {
        return isUnderlined;
    }
}

