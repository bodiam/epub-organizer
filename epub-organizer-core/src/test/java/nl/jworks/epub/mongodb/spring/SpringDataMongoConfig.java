package nl.jworks.epub.mongodb.spring;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = {
        "nl.jworks.epub.mongodb.spring.repository",
        "nl.jworks.epub.mongodb.spring.service" }
)
public class SpringDataMongoConfig {

    @Bean
    public Mongo mongo() throws UnknownHostException, MongoException {
        return new Mongo("127.0.0.1");
    }

    @Bean
    public SimpleMongoDbFactory mongoDbFactory() throws UnknownHostException,
            MongoException {
//		UserCredentials userCredentials = new UserCredentials("dev", "devpassword");
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo(), "epub"
//				userCredentials
        );
        factory.setWriteConcern(WriteConcern.SAFE);
        return factory;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

}
