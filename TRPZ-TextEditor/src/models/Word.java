package models;

import java.util.List;

public class Word {
    private String colorCode;
    private List<Symbol> symbols;

    public Word(String colorCode, List<Symbol> symbols) {
        this.colorCode = colorCode;
        this.symbols = symbols;
    }

    public String getColorCode() { return colorCode; }
    public List<Symbol> getSymbols() { return symbols; }
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : symbols) sb.append(s.getCharacter());
        return sb.toString();
    }
}


