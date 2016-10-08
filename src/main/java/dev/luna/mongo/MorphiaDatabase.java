package dev.luna.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Service;

/**
 * Created by alberto on 07/10/16.
 */
@Service
public class MorphiaDatabase {
    final Morphia morphia = new Morphia();
    public Datastore datastore;

    private MorphiaDatabase() {
        morphia.mapPackage("dev.luna.models");
        datastore = morphia.createDatastore(new MongoClient(), "retroboard");
        datastore.ensureIndexes();
    }

}
