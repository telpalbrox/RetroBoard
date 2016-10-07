package dev.luna.sokets;

import dev.luna.daos.BoardsDAO;
import dev.luna.models.ListAction;
import dev.luna.models.Section;
import dev.luna.rethink.RethinkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by alberto on 04/10/16.
 */
@Controller
public class WebSocketsController {
    private SimpMessagingTemplate template;

    @Autowired
    public WebSocketsController(SimpMessagingTemplate template) {
        // RethinkConnection.getInstance().setTemplate(template);
        this.template = template;
    }

    @MessageMapping("/boards/{id}/sections/create")
    public void createSection(Map<String, String> request, @DestinationVariable("id") String id) throws Exception {
        Section section = BoardsDAO.getInstance().createSection(id, request.get("name"));
        template.convertAndSend("/boards/" + id, section);
    }
}
