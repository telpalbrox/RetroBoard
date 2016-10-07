package dev.luna.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alberto on 06/10/16.
 */
public class Board {
    private String id;
    private String name;
    private List<Section> sections;

    public Board() {

    }

    public Board(String id) {
        this.id = id;
        this.sections = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
