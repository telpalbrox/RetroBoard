package dev.luna.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alberto on 07/10/16.
 */
@Entity("sections")
public class Section {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String uuid;
    private String name;
    @Reference
    private List<Ticket> tickets = new ArrayList<>();

    public Section() {

    }
    
    public Section(String uuid) {
        this.uuid = uuid;
        this.tickets = new ArrayList<>();
    }

    public boolean removeTicket(Ticket ticket) {
        Ticket ticketToRemove = getTickets().stream().filter(
                ticketSection -> ticketSection.getUuid().equals(ticket.getUuid())
        ).findFirst().get();
        return getTickets().remove(ticketToRemove);
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
