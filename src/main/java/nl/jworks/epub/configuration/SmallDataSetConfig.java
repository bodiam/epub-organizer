package nl.jworks.epub.configuration;

import nl.jworks.epub.loader.Broker;
import nl.jworks.epub.loader.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Configuration
@Profile("smalldata")
public class SmallDataSetConfig {

    @Autowired
    private Broker<File> broker;

    @Bean
    public Producer producer() {
        return new Producer("src/main/resources/books/10", broker);
    }

}
