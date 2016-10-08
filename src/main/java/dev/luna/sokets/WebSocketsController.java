package dev.luna.sokets;

import dev.luna.dao.BoardsDAO;
import dev.luna.models.Section;
import dev.luna.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by alberto on 04/10/16.
 */
@Controller
public class WebSocketsController {
    private BoardsDAO boardsDAO;

    @Autowired
    public WebSocketsController(BoardsDAO boardsDAO) {
        this.boardsDAO = boardsDAO;
    }

    @MessageMapping("/boards/{uuid}/sections/create")
    public void createSection(Map<String, String> request, @DestinationVariable("uuid") String uuid) throws Exception {
        Section section = boardsDAO.createSection(uuid, request.get("name"));
    }

    @MessageMapping("/boards/{boardUuid}/sections/{sectionUuid}/tickets/create")
    public void createTicket(Map<String, String> request, @DestinationVariable("boardUuid") String boardUuid, @DestinationVariable("sectionUuid") String sectionUuid) {
        Ticket ticket = boardsDAO.createTicket(boardUuid, sectionUuid, request.get("content"));
    }

    @MessageMapping("/boards/{boardUuid}/sections/{sectionUuid}/tickets/{ticketUuid}/delete")
    public void deleteTicket(@DestinationVariable("boardUuid") String boardUuid, @DestinationVariable("sectionUuid") String sectionUuid, @DestinationVariable("ticketUuid") String ticketUuid) {
        boardsDAO.deleteTicket(boardUuid, sectionUuid, ticketUuid);
    }

    @MessageMapping("/boards/{boardUuid}/sections/{sectionUuid}/delete")
    public void deleteSection(@DestinationVariable("boardUuid") String boardUuid, @DestinationVariable("sectionUuid") String sectionUuid) {
        boardsDAO.deleteSection(boardUuid, sectionUuid);
    }
}
