package models;

import java.util.List;

public class Settings {
    private int id;
    private List<Macro> macros; // Список макросів
    private List<Snippet> snippets; // Список сніпетів
    private List<SyntaxHighlighter> syntaxHighlighters; // Список підсвічувань
    private List<Bookmark> bookmarks;// Список закладок
    private Encoding encoding;

    public Settings(int id, List<Macro> macros, List<Snippet> snippets, List<SyntaxHighlighter> syntaxHighlighters,
                    List<Bookmark> bookmarks, Encoding encoding) {
        this.id = id;
        this.macros = macros;
        this.snippets = snippets;
        this.syntaxHighlighters = syntaxHighlighters;
        this.bookmarks = bookmarks;
        this.encoding = encoding;
    }

// Гетери та сетери (не включені для стислості)
}




