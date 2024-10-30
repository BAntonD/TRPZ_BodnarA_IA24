package models;

public class File {
    private int id;
    private String name;
    private Settings settings; // Зв'язок з налаштуваннями
    private int size;

    public File(int id, String name, Settings settings, int size) {
        this.id = id;
        this.name = name;
        this.settings = settings;
        this.size = size;
    }

    // Гетери та сетери (не включені для стислості)
}


