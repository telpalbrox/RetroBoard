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
        r.db("test").tableCreate("lists").run(conn);
        new Thread(this).start();
    }

    public void addList(String id) {
        Cursor cursor = r.table("lists").filter((row) -> row.g("id").eq(id)).run(conn);
        if (!cursor.toList().isEmpty()) {
            return;
        }
        r.table("lists").insert(r.hashMap("id", id).with("items", r.array())).run(conn);
    }

    public void addItemToList(String id, String item) {
        r.table("lists").filter(
                row -> row.g("id").eq(id)
        ).update(row -> r.hashMap("items", row.g("items").append(item))).run(conn);
        template.convertAndSend("/list/" + id, item);
    }

    public void setTemplate(SimpMessagingTemplate template) {
        if (template == null) {
            return;
        }
        this.template = template;
    }

    @Override
    public void run() {
        Cursor changeCursor = r.table("lists").changes().run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
    }
}
