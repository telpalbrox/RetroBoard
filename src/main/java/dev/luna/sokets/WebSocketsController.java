package dev.luna.sokets;

import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.mongo.MorphiaDatabase;
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

    @Autowired
    public WebSocketsController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/boards/{id}/sections/create")
    public void createSection(Map<String, String> request, @DestinationVariable("id") long id) throws Exception {
        Section section = new Section();
        section.setName(request.get("name"));
        MorphiaDatabase.getInstance().datastore.save(section);
        Board board = MorphiaDatabase.getInstance().datastore.get(Board.class, id);
        board.getSections().add(section);
        MorphiaDatabase.getInstance().datastore.save(board);
        template.convertAndSend("/boards/" + id, section);
    }
}
