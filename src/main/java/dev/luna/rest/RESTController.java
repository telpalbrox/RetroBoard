package dev.luna.rest;

import dev.luna.dao.BoardsDAO;
import dev.luna.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by alberto on 06/10/16.
 */
@RestController
public class RESTController {
    private BoardsDAO boardsDAO;

    @Autowired
    public RESTController(BoardsDAO boardsDAO) {
        this.boardsDAO = boardsDAO;
    }

    @PostMapping("/boards")
    public CreateBoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        Board board = boardsDAO.createBoard(request.getName());
        return new CreateBoardResponse(board.getUuid());
    }

    @PostMapping("/boards/{boardUuid}/sections")
    public Section createSection(@RequestBody Map<String, String> request, @PathVariable("boardUuid") String boardUuid) {
        return boardsDAO.createSection(boardUuid, request.get("name"));
    }

    @PostMapping("/boards/{boardUuid}/sections/{sectionUuid}/tickets")
    public Ticket createTicket(@RequestBody Map<String, String> request, @PathVariable("boardUuid") String boardUuid, @PathVariable("sectionUuid") String sectionUuid) {
        return boardsDAO.createTicket(boardUuid, sectionUuid, request.get("content"));
    }

    @DeleteMapping("/boards/{boardUuid}/sections/{sectionUuid}/tickets/{ticketUuid}")
    public void deleteTicket(@PathVariable("boardUuid") String boardUuid, @PathVariable("sectionUuid") String sectionUuid, @PathVariable("ticketUuid") String ticketUuid) {
        boardsDAO.deleteTicket(boardUuid, sectionUuid, ticketUuid);
    }

    @GetMapping("/boards")
    public List<Board> getAllBoards() {
        return boardsDAO.getAllBoards();
    }
}
