package models;

public class Bookmark {
    private int id; // Унікальний ідентифікатор закладки
    private boolean state; // Стан (true, якщо закладка активна)
    private int lineNumber; // Номер рядка, на якому збережена закладка

    public Bookmark(int id) {
        this.id = id;
        this.state = false; // Початковий стан - неактивна закладка
    }

    // Геттери та сеттери
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}



