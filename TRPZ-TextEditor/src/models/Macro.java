package models;

public class Macro {
    private int id;
    private String name;
    private boolean isBold;
    private boolean isItalic;
    private boolean isUnderlined;
    // Зберігання активного стану запису макросів
    private boolean isRecording = false;



    // Конструктор
    public Macro(int id, String name, boolean isBold, boolean isItalic, boolean isUnderlined) {
        this.id = id;
        this.name = name;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderlined = isUnderlined;
    }

    // Геттер і сеттер
    public boolean isRecording() { return isRecording; }
    public void setRecording(boolean recording) { isRecording = recording; }

    // Гетери та сетери
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isBold() { return isBold; }
    public void setBold(boolean bold) { isBold = bold; }

    public boolean isItalic() { return isItalic; }
    public void setItalic(boolean italic) { isItalic = italic; }

    public boolean isUnderlined() { return isUnderlined; }
    public void setUnderlined(boolean underlined) { isUnderlined = underlined; }

    @Override
    public String toString() {
        return name; // Використовується для відображення в списку
    }

}



