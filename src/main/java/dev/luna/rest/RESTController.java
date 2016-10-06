package dev.luna.rest;

import dev.luna.daos.BoardsDAO;
import dev.luna.models.CreateBoardResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by alberto on 06/10/16.
 */
@RestController
public class RESTController {

    @PostMapping("/boards")
    public CreateBoardResponse createBoard(@RequestBody String name) {
        String boardId = BoardsDAO.getInstance().createBoard(name);
        return new CreateBoardResponse(boardId);
    }
}
