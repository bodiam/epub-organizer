package nl.jworks.epub.configuration;

import nl.jworks.epub.loader.Broker;
import nl.jworks.epub.loader.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("bigdata")
public class BigDataSetConfig {

    @Autowired
    private Broker broker;

    @Bean
    public Producer producer() {
        return new Producer("/Users/erikp/Desktop/books/1000", broker);
    }

}
