package dev.luna.models;

/**
 * Created by alberto on 07/10/16.
 */
public class Ticket {
    private String id;
    private String content;

    public Ticket() {

    }

    public Ticket(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
