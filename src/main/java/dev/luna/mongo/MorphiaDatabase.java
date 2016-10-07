package dev.luna.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by alberto on 07/10/16.
 */
public class MorphiaDatabase {
    final Morphia morphia = new Morphia();
    public Datastore datastore;

    private static MorphiaDatabase instance = null;

    static public MorphiaDatabase getInstance() {
        if (instance == null) {
            instance = new MorphiaDatabase();
        }
        return instance;
    }

    private MorphiaDatabase() {
        morphia.mapPackage("dev.luna.models");
        datastore = morphia.createDatastore(new MongoClient(), "retroboard");
        datastore.ensureIndexes();
    }

}
