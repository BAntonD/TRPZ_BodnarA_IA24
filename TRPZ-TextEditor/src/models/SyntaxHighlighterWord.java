package models;

public class SyntaxHighlighterWord {
    private final int syntaxHighlighterId;  // ID підсвітки
    private final String word;              // Слово для підсвітки
    private final int idColor;              // ID кольору

    public SyntaxHighlighterWord(int syntaxHighlighterId, String word, int idColor) {
        this.syntaxHighlighterId = syntaxHighlighterId;
        this.word = word;
        this.idColor = idColor;
    }

    public int getSyntaxHighlighterId() {
        return syntaxHighlighterId;
    }

    public String getWord() {
        return word;
    }

    public int getIdColor() {
        return idColor;
    }

}
