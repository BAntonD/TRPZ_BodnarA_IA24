package models;

public class File {
    private int id;
    private String name;
    private Settings settings; // Зв'язок з налаштуваннями

    public File(int id, String name, Settings settings) {
        this.id = id;
        this.name = name;
        this.settings = settings;
    }

    // Гетери та сетери (не включені для стислості)
}


