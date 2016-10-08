package dev.luna.sokets;

import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.models.Ticket;
import dev.luna.mongo.MorphiaDatabase;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by alberto on 04/10/16.
 */
@Controller
public class WebSocketsController {
    private SimpMessagingTemplate template;
    private Datastore datastore;

    @Autowired
    public WebSocketsController(SimpMessagingTemplate template) {
        this.template = template;
        this.datastore = MorphiaDatabase.getInstance().datastore;
    }

    @MessageMapping("/boards/{id}/sections/create")
    public void createSection(Map<String, String> request, @DestinationVariable("id") long id) throws Exception {
        Section section = new Section();
        section.setName(request.get("name"));
        datastore.save(section);
        Board board = MorphiaDatabase.getInstance().datastore.get(Board.class, id);
        board.getSections().add(section);
        datastore.save(board);
        template.convertAndSend("/boards/" + id, section);
    }

    @MessageMapping("/boards/{boardId}/sections/{sectionId}/tickets/create")
    public void createTicket(Map<String, String> request, @DestinationVariable("boardId") long boardId, @DestinationVariable("sectionId") long sectionId) {
        Ticket ticket = new Ticket();
        ticket.setContent(request.get("content"));
        Board board = datastore.get(Board.class, boardId);
        Section section = board.getSections().stream().filter(boardSection -> boardSection.getId() == sectionId).findFirst().get();
        section.getTickets().add(ticket);
        datastore.save(ticket);
        datastore.save(section);
        template.convertAndSend("/boards/" + boardId, ticket);
    }
}
