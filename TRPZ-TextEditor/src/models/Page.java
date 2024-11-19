package models;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int pageNumber;
    private List<String> lines; // Текст рядків на сторінці

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        this.lines = new ArrayList<>();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public List<String> getLines() {
        return lines;
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public String getLine(int lineNumber) {
        if (lineNumber > 0 && lineNumber <= lines.size()) {
            return lines.get(lineNumber - 1); // Нумерація рядків з 1
        }
        return null; // Рядок не знайдено
    }
}

