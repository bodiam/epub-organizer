package nl.jworks.epub.dropwizard;

import com.mongodb.Mongo;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class BookService extends Service<BookConfiguration> {

    public static void main(String[] args) throws Exception {
        new BookService().run(args);
    }

    @Override
    public void initialize(Bootstrap<BookConfiguration> bootstrap) {
        bootstrap.setName("book");
    }

    @Override
    public void run(BookConfiguration configuration, Environment environment) throws Exception {
//        Mongo

    }
}
