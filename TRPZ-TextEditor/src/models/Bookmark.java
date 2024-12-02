package models;

public class Bookmark {
    private int id;
    private Settings settings;
    private int pageNumber;
    private int lineNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Bookmark(int id, Settings settings, int pageNumber, int lineNumber) {
        this.id = id;
        this.settings = settings;
        this.pageNumber = pageNumber;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", settings=" + settings +
                ", pageNumber=" + pageNumber +
                ", lineNumber=" + lineNumber +
                '}';
    }
}


