package nl.jworks.epub.configuration;


import nl.jworks.epub.loader.Broker;
import nl.jworks.epub.loader.Consumer;
import nl.jworks.epub.logic.names.BookProducer;
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

    @Bean
    @Scope("prototype")
    public Consumer consumer() {
        return new Consumer(UUID.randomUUID().toString(), broker, bookProducer, bookService);
    }

//    @Bean
//    public Producer producer() {
//        return new Producer("/Users/erikp/Desktop/books/1000", broker);
//        return new Producer("src/main/resources/books/1000", broker);
//    }
}
