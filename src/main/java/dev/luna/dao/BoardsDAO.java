package dev.luna.dao;

import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.models.Ticket;
import dev.luna.mongo.MorphiaDatabase;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by alberto on 08/10/16.
 */
@Repository
public class BoardsDAO {
    private Datastore datastore;


    @Autowired
    public BoardsDAO(MorphiaDatabase morphiaDatabase) {
        datastore = morphiaDatabase.datastore;
    }

    public Board createBoard(String name) {
        Board board = new Board(UUID.randomUUID().toString());
        board.setName(name);
        datastore.save(board);
        return board;
    }

    public Board getBoardByName(String name) {
        return datastore.createQuery(Board.class).field("name").equal(name).get();
    }

    public Section createSection(String boardUuid, String name) {
        Section section = new Section(UUID.randomUUID().toString());
        section.setName(name);
        datastore.save(section);
        Board board = datastore.createQuery(Board.class).field("uuid").equal(boardUuid).get();
        board.getSections().add(section);
        datastore.save(board);
        return section;
    }


    public Ticket createTicket(String boardUuid, String sectionUuid, String content) {
        Ticket ticket = new Ticket(UUID.randomUUID().toString());
        ticket.setContent(content);
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        section.getTickets().add(ticket);
        datastore.save(ticket);
        datastore.save(section);
        return ticket;
    }

    public void deleteTicket(String boardUuid, String sectionUuid, String ticketUuid) {
        Ticket ticket = datastore.createQuery(Ticket.class).field("uuid").equal(ticketUuid).get();
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        section.removeTicket(ticket);
        datastore.save(section);
        datastore.delete(ticket);
    }

    public void deleteSection(String boardUuid, String sectionUuid) {
        Board board = datastore.createQuery(Board.class).field("uuid").equal(boardUuid).get();
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        board.removeSection(section);
        for (Ticket ticket : section.getTickets()) {
            deleteTicket(boardUuid, sectionUuid, ticket.getUuid());
        }
        datastore.save(board);
        datastore.delete(section);
    }

    public List<Board> getAllBoards() {
        return datastore.find(Board.class).asList();
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
