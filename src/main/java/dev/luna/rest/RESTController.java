package dev.luna.rest;

import dev.luna.models.Board;
import dev.luna.models.CreateBoardRequest;
import dev.luna.models.CreateBoardResponse;
import dev.luna.mongo.MorphiaDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by alberto on 06/10/16.
 */
@RestController
public class RESTController {

    @PostMapping("/boards")
    public CreateBoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        Board board = new Board();
        board.setName(request.getName());
        MorphiaDatabase.getInstance().datastore.save(board);
        return new CreateBoardResponse(board.getId());
    }

    @GetMapping("/boards")
    public List<Board> getAllBoards() {
        return MorphiaDatabase.getInstance().datastore.createQuery(Board.class).asList();
    }
}
