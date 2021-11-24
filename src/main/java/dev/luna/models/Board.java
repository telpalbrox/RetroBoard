package dev.luna.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by alberto on 06/10/16.
 */
@Entity("boards")
public class Board {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String uuid;
    private String name;
    @Reference
    private List<Section> sections;
    
    public Board() {
        
    }

    public Board(String uuid) {
        this.uuid = uuid;
    }

    public boolean removeSection(Section section) {
        Section sectionToRemove = getSections().stream().filter(
                sectionBoard -> sectionBoard.getUuid().equals(section.getUuid())
        ).findFirst().get();
        return getSections().remove(sectionToRemove);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
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
}
