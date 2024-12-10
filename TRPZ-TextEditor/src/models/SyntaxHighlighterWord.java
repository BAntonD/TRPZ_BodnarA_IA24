package models;

public class SyntaxHighlighterWord {
    private int id;
    private int syntaxHighlighterId;
    private String word;
    private String color;

    public SyntaxHighlighterWord(int id, int syntaxHighlighterId, String word, String color) {
        this.id = id;
        this.syntaxHighlighterId = syntaxHighlighterId;
        this.word = word;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSyntaxHighlighterId() {
        return syntaxHighlighterId;
    }

    public void setSyntaxHighlighterId(int syntaxHighlighterId) {
        this.syntaxHighlighterId = syntaxHighlighterId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
