package nl.jworks.epub.dropwizard;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.json.ObjectMapperFactory;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.dropwizard.health.BookHealthCheck;
import nl.jworks.epub.dropwizard.resources.CreateBookResource;
import nl.jworks.epub.dropwizard.resources.ListBooksResource;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;

public class BookService extends Service<BookConfiguration> {

    public static void main(String[] args) throws Exception {
        new BookService().run(new String[]{"server", "src/main/resources/book.yml"});
    }

    @Override
    public void initialize(Bootstrap<BookConfiguration> bootstrap) {
        bootstrap.setName("book");
    }

    @Override
    public void run(BookConfiguration configuration, Environment environment) throws Exception {

        MongoClient mongoClient = new MongoClient(configuration.getMongohost(), configuration.getMongoport());
        DB db = mongoClient.getDB(configuration.getMongodb());

        JacksonDBCollection<Book, ObjectId> books = JacksonDBCollection.wrap(db.getCollection("books"), Book.class, ObjectId.class);

        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        environment.manage(mongoManaged);

        environment.addHealthCheck(new BookHealthCheck(mongoClient));

        environment.addResource(new CreateBookResource(books));
        environment.addResource(new ListBooksResource(books));

        if (configuration.getPrettyPrint()) {
            ObjectMapperFactory factory = environment.getObjectMapperFactory();
            factory.enable(SerializationFeature.INDENT_OUTPUT);
        }
    }

}
