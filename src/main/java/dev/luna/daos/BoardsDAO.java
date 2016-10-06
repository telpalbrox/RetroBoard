package dev.luna.daos;

import com.rethinkdb.RethinkDB;
import dev.luna.rethink.RethinkConnection;
import org.omg.CORBA.Object;

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

    public String createBoard(String name) {
		Map result = r.table("boards").insert(r.hashMap("name", name)).run(connection.conn);
		List generatedKeys = (List) result.get("generated_keys");
        return (String) generatedKeys.get(0);
    }
}
