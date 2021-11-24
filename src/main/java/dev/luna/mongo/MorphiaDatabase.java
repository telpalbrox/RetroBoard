package dev.luna.mongo;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.springframework.stereotype.Service;

/**
 * Created by alberto on 07/10/16.
 */
@Service
public class MorphiaDatabase {
    public Datastore datastore;

    private MorphiaDatabase() {
        String connectionString = System.getenv("MONGO_CONNECTION_STRING");
        if (connectionString != null) {
            datastore = Morphia.createDatastore(MongoClients.create(connectionString), "retroboard");
        } else {
            datastore = Morphia.createDatastore("retroboard");
        }
        datastore.ensureIndexes();
    }

}
