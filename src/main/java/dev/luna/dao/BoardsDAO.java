package dev.luna.dao;

import dev.morphia.query.experimental.filters.Filters;
import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.models.SocketResponse;
import dev.luna.models.Ticket;
import dev.luna.mongo.MorphiaDatabase;
import dev.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by alberto on 08/10/16.
 */
@Repository
public class BoardsDAO {
    private static final Logger logger = Logger.getLogger(BoardsDAO.class.getName());
    private Datastore datastore;
    private final SimpMessagingTemplate socket;
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
        return datastore.find(Board.class).filter(Filters.eq("name", name)).first();
    }

    public Section createSection(String boardUuid, String name) {
        Section section = new Section(UUID.randomUUID().toString());
        section.setName(name);
        datastore.save(section);
        Board board = datastore.find(Board.class).filter(Filters.eq("uuid", boardUuid)).first();
        if (board == null) {
            logger.warning("Board " + boardUuid + " not found");
            return null;
        }
        board.getSections().add(section);
        datastore.save(board);
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.ADDED, "section", section));
        return section;
    }


    public Ticket createTicket(String boardUuid, String sectionUuid, String content) {
        Ticket ticket = new Ticket(UUID.randomUUID().toString());
        ticket.setContent(content);
        Section section = datastore.find(Section.class).filter(Filters.eq("uuid", sectionUuid)).first();
        if (section == null) {
            logger.warning("Section " + sectionUuid + " not found");
            return null;
        }
        section.getTickets().add(ticket);
        ticket.setSectionUuid(section.getUuid());
        datastore.save(ticket);
        datastore.save(section);
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.ADDED, "ticket", ticket));
        return ticket;
    }

    public void deleteTicket(String boardUuid, String sectionUuid, String ticketUuid, boolean emit) {
        Ticket ticket = datastore.find(Ticket.class).filter(Filters.eq("uuid", ticketUuid)).first();
        Section section = datastore.find(Section.class).filter(Filters.eq("uuid", sectionUuid)).first();
        if (ticket == null || section == null) {
            logger.warning("Ticket or section not found: " + ticketUuid + ", " + sectionUuid);
            return;
        }
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
        Board board = datastore.find(Board.class).filter(Filters.eq("uuid", boardUuid)).first();
        Section section = datastore.find(Section.class).filter(Filters.eq("uuid", sectionUuid)).first();
        if (board == null || section == null) {
            logger.warning("Board or section not found: " + boardUuid + ", " + sectionUuid);
            return;
        }
        board.removeSection(section);
        for (Ticket ticket : section.getTickets()) {
            deleteTicket(boardUuid, sectionUuid, ticket.getUuid(), false);
        }
        datastore.save(board);
        datastore.delete(section);
        socket.convertAndSend(TOPIC + boardUuid, new SocketResponse(SocketResponse.Type.REMOVED, "section", section));
    }

    public List<Board> getAllBoards() {
        return datastore.find(Board.class).stream().collect(Collectors.toList());
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
