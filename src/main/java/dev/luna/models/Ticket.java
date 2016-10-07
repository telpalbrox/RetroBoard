package dev.luna.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by alberto on 07/10/16.
 */
@Entity("tickets")
public class Ticket {
    @Id
    private long id;
    private String content;

    public Ticket() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
