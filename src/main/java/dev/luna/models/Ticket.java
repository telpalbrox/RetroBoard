package dev.luna.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;

/**
 * Created by alberto on 07/10/16.
 */
@Entity("tickets")
public class Ticket {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String uuid;
    private String content;
    @Transient
    private String sectionUuid;

    public Ticket() {

    }
    
    public Ticket(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSectionUuid(String sectionUuid) {
        this.sectionUuid = sectionUuid;
    }

    public String getSectionUuid() {
        return sectionUuid;
    }
}
