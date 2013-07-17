package nl.jworks.epub.dropwizard;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;

public class MongoManaged implements Managed {

    private MongoClient mongoClient;

    public MongoManaged(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}
