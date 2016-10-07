package dev.luna.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alberto on 07/10/16.
 */
public class Section {
    private String id;
    private String name;
    private List<Ticket> tickets;

    public Section() {

    }

    public Section(String id) {
        this.id = id;
        this.tickets = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
