package dev.luna.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alberto on 07/10/16.
 */
@Service
public class MorphiaDatabase {
    final Morphia morphia = new Morphia();
    public Datastore datastore;

    private MorphiaDatabase() {
        morphia.mapPackage("dev.luna.models");
        String host = System.getenv("MONGO_HOST");
        String user = System.getenv("MONGO_USER");
        String password = System.getenv("MONGO_PASSWORD");
        String portToParse = System.getenv("MONGO_PORT");
        if (user != null && password != null && portToParse != null && host != null) {
            int port = Integer.parseInt(System.getenv("MONGO_PORT"));
            ServerAddress serverAddress = new ServerAddress(host, port);
            MongoCredential credential = MongoCredential.createCredential(user, "retroboard", password.toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            datastore = morphia.createDatastore(new MongoClient(serverAddress, credentials), "retroboard");
        } else {
            datastore = morphia.createDatastore(new MongoClient(), "retroboard");
        }
        datastore.ensureIndexes();
    }

}
