package models;

public class SnippetList {
    private int id; // id для сніпетного списку
    private String name; // назва сніпетного списку

    // Конструктор
    public SnippetList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттери та сеттери
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
