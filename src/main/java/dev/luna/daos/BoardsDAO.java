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
            boards.add(getBoardFromMap(mapBoard));
        }

        return boards;
    }

    public Board createBoard(String name) {
		Map result = r.table("boards").insert(r.hashMap("name", name).with("sections", r.array())).run(connection.conn);
		List generatedKeys = (List) result.get("generated_keys");
        return new Board((String) generatedKeys.get(0));
    }

    public Section createSection(String boardId, String name) {
        String uuid = r.uuid().run(connection.conn);
        Section section = new Section(uuid);
        section.setName(name);
        r.table("boards").get(boardId).update(
                row -> r.hashMap("sections", row.g("sections").append(r.hashMap("id", uuid).with("name", name)))
        ).run(connection.conn);
        return section;
    }

    private Board getBoardFromMap(Map boardMap) {
        Board board = new Board((String) boardMap.get("id"));
        board.setName((String) boardMap.get("name"));
        List<Map> sectionsMaps = (List<Map>) boardMap.get("sections");
        if (sectionsMaps != null) {
            for (Map sectionMap : sectionsMaps) {
                board.getSections().add(getSectionFromMap(sectionMap));
            }
        }
        return board;
    }

    private Section getSectionFromMap(Map sectionMap) {
        Section section = new Section((String) sectionMap.get("id"));
        section.setName((String) sectionMap.get("name"));
        List<Map<String, String>> ticketsMaps = (List<Map<String, String>>) sectionMap.get("tickets");
        if (ticketsMaps != null) {
            for (Map<String, String> ticketMap : ticketsMaps) {
                section.getTickets().add(getTicketFromMap(ticketMap));
            }
        }
        return section;
    }

    private Ticket getTicketFromMap(Map<String, String> ticketMap) {
        Ticket ticket = new Ticket(ticketMap.get("id"));
        ticket.setContent(ticketMap.get("content"));
        return  ticket;
    }
}
