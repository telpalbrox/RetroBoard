package dev.luna.sokets;

import dev.luna.models.ListAction;
import dev.luna.rethink.RethinkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by alberto on 04/10/16.
 */
@Controller
public class WebSocketsController {

    @Autowired
    public WebSocketsController(SimpMessagingTemplate template) {
        RethinkConnection.getInstance().setTemplate(template);
    }

    @MessageMapping("/list/{id}")
    public void greeting(ListAction action, @DestinationVariable("id") String id) throws Exception {
        System.out.print(action.getType());
        Thread.sleep(1000);
        RethinkConnection.getInstance().addList(id);
        RethinkConnection.getInstance().addItemToList(id, action.getPayload());
    }
}
