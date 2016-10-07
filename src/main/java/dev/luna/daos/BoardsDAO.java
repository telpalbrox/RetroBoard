package dev.luna.daos;

import com.rethinkdb.RethinkDB;

import com.rethinkdb.net.Cursor;
import dev.luna.models.Board;
import dev.luna.models.Section;
import dev.luna.models.Ticket;
import dev.luna.rethink.RethinkConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alberto on 06/10/16.
 */
public class BoardsDAO {
    private static BoardsDAO instance = null;
    public static final RethinkDB r = RethinkDB.r;

    private RethinkConnection connection;

    public static BoardsDAO getInstance() {
        if (instance == null) {
            instance = new BoardsDAO();
        }
        return instance;
    }

    private BoardsDAO() {
        this.connection = RethinkConnection.getInstance();
    }

    public List<Board> getAllBoards() {
        // TODO crappy code
        Cursor<Map> cursor = r.table("boards").run(connection.conn);
        List<Board> boards = new ArrayList<>();
        for (Map mapBoard : cursor) {
            Board board = new Board((String) mapBoard.get("id"));
            board.setName((String) mapBoard.get("name"));
            boards.add(board);
            for (Map mapSection : (List<Map>) mapBoard.get("sections")) {
                Section section = new Section((String) mapSection.get("id"));
                board.getSections().add(section);
                if (mapBoard.get("tickets") != null) {
                    for (Map mapTicket : (List<Map>) mapSection.get("tickets")) {
                        Ticket ticket = new Ticket((String) mapTicket.get("id"));
                        ticket.setContent((String) mapTicket.get("content"));
                    }
                }
            }
        }

        return boards;
    }

    public Board createBoard(String name) {
		Map result = r.table("boards").insert(r.hashMap("name", name).with("sections", r.array())).run(connection.conn);
		List generatedKeys = (List) result.get("generated_keys");
        return new Board((String) generatedKeys.get(0));
    }
}
