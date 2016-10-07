package dev.luna.rethink;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by alberto on 05/10/16.
 */
public class RethinkConnection implements Runnable {
    public static final RethinkDB r = RethinkDB.r;
    static private RethinkConnection connection = null;

    public Connection conn;
    private SimpMessagingTemplate template = null;

    public static RethinkConnection getInstance() {
        if (connection == null) {
            connection = new RethinkConnection();
        }

        return connection;
    }

    private RethinkConnection() {
        conn = r.connection().hostname("localhost").port(28015).connect();
        new Thread(this).start();
    }

    @Override
    public void run() {
        Cursor changeCursor = r.table("lists").changes().run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
    }
}
