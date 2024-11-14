package models;


public class Snippet {
    private int id; // Унікальний ідентифікатор сніпету
    private String trigger; // Вміст сніпету
    private String content; // Мова програмування


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Snippet(String trigger, String content) {
        this.trigger = trigger;
        this.content = content;
    }
}



