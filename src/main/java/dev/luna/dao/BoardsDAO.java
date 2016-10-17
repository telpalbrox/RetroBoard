package dev.luna.dao;

import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.models.SocketResponse;
import dev.luna.models.Ticket;
import dev.luna.mongo.MorphiaDatabase;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by alberto on 08/10/16.
 */
@Repository
public class BoardsDAO {
    private Datastore datastore;
    private SimpMessagingTemplate socket;
    private final String TOPIC = "/boards/";

    @Autowired
    public BoardsDAO(MorphiaDatabase morphiaDatabase, SimpMessagingTemplate template) {
        datastore = morphiaDatabase.datastore;
        socket = template;
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
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.ADDED, "section", section));
        return section;
    }


    public Ticket createTicket(String boardUuid, String sectionUuid, String content) {
        Ticket ticket = new Ticket(UUID.randomUUID().toString());
        ticket.setContent(content);
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        section.getTickets().add(ticket);
        ticket.setSectionUuid(section.getUuid());
        datastore.save(ticket);
        datastore.save(section);
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.ADDED, "ticket", ticket));
        return ticket;
    }

    public void deleteTicket(String boardUuid, String sectionUuid, String ticketUuid, boolean emit) {
        Ticket ticket = datastore.createQuery(Ticket.class).field("uuid").equal(ticketUuid).get();
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        section.removeTicket(ticket);
        ticket.setSectionUuid(section.getUuid());
        datastore.save(section);
        datastore.delete(ticket);
        if (emit) {
            socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.REMOVED, "ticket", ticket));
        }
    }

    public void deleteTicket(String uuid, String sectionUuid, String ticketUuid) {
        deleteTicket(uuid, sectionUuid, ticketUuid, true);
    }

    public void deleteSection(String boardUuid, String sectionUuid) {
        Board board = datastore.createQuery(Board.class).field("uuid").equal(boardUuid).get();
        Section section = datastore.createQuery(Section.class).field("uuid").equal(sectionUuid).get();
        board.removeSection(section);
        for (Ticket ticket : section.getTickets()) {
            deleteTicket(boardUuid, sectionUuid, ticket.getUuid(), false);
        }
        datastore.save(board);
        datastore.delete(section);
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.REMOVED, "section", section));
    }

    public List<Board> getAllBoards() {
        return datastore.find(Board.class).asList();
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
