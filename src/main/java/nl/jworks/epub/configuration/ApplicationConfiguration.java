package nl.jworks.epub.configuration;


import nl.jworks.epub.loader.Broker;
import nl.jworks.epub.loader.Consumer;
import nl.jworks.epub.loader.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = { "nl.jworks.epub" })
public class ApplicationConfiguration {

    @Autowired
    private Broker<File> broker;

    @Bean @Scope("prototype")
    public Consumer consumer() {
        return new Consumer(UUID.randomUUID().toString(), broker);
    }

    @Bean
    public Producer producer() {
        return new Producer("/Users/erikp/Desktop/books/1", broker);
    }
}
