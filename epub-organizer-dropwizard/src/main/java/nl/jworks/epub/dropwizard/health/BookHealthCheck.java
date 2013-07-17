package nl.jworks.epub.dropwizard.health;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.yammer.metrics.core.HealthCheck;

public class BookHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public BookHealthCheck(MongoClient mongoClient) {
        super("book");

        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        try {
            mongoClient.getDatabaseNames();
        } catch (MongoException e) {
            return Result.unhealthy(e.getMessage());
        }
        return Result.healthy();
    }
}
