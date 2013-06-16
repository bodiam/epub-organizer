package nl.jworks.epub.configuration;


import nl.jworks.epub.loader.Broker;
import nl.jworks.epub.loader.Consumer;
import nl.jworks.epub.loader.Producer;
import nl.jworks.epub.loader.Start;
import nl.jworks.epub.logic.names.BookProducer;
import nl.jworks.epub.persistence.spring.BinaryRepository;
import nl.jworks.epub.persistence.spring.BookRepository;
import nl.jworks.epub.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import java.io.File;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = {
        "nl.jworks.epub",
        "nl.jworks.epub.persistence.spring"
})
@Import({SpringDataMongoConfiguration.class, BigDataSetConfig.class, SmallDataSetConfig.class})
public class ApplicationConfiguration {

    @Autowired
    private Broker<File> broker;

    @Autowired
    private BookProducer bookProducer;

    @Autowired
    private BookService bookService;

    @Autowired
    private Producer producer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BinaryRepository binaryRepository;

    @Bean
    @Scope("prototype")
    public Consumer consumer() {
        return new Consumer(UUID.randomUUID().toString(), broker, bookProducer, bookService);
    }

    @Bean
    public Start start() {
        // return new anonymous implementation of Start with createConsumer() overridden
        // to return a new prototype Consumer object
        return new Start(producer, bookRepository, binaryRepository) {
            @Override
            protected Consumer createConsumer() {
                return consumer();
            }
        };
    }

}
