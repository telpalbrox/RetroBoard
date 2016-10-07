package dev.luna.rest;

import dev.luna.daos.BoardsDAO;
import dev.luna.models.Board;
import dev.luna.models.CreateBoardRequest;
import dev.luna.models.CreateBoardResponse;
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
        Board createdBoard = BoardsDAO.getInstance().createBoard(request.getName());
        return new CreateBoardResponse(createdBoard.getId());
    }

    @GetMapping("/boards")
    public List<Board> getAllBoards() {
        return BoardsDAO.getInstance().getAllBoards();
    }
}
