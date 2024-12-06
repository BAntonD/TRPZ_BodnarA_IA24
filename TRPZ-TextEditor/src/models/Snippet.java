package models;


public class Snippet {
    private String trigger; // тригер сніпета
    private String content; // контент сніпета

    public Snippet(String trigger, String content) {
        this.trigger = trigger;
        this.content = content;
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


}




