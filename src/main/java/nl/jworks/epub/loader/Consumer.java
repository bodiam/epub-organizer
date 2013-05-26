package nl.jworks.epub.loader;

import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.names.BookProducer;
import nl.jworks.epub.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.File;
import java.io.IOException;

public class Consumer implements Runnable {

    private static Logger log = LoggerFactory.getLogger(Consumer.class);

    private String name;
    private Broker<File> broker;
    private BookProducer bookProducer;
    private BookService bookService;

    public Consumer(String name, Broker<File> broker, BookProducer bookProducer, BookService bookService) {
        this.name = name;
        this.broker = broker;
        this.bookProducer = bookProducer;
        this.bookService = bookService;
    }

    @Override
    public void run() {
        try {
            File data = broker.get();

            MDC.put("book-filename", data.getName());

            while (broker.continueProducing || data != null) {
                log.debug("Consumer {} processed data {} from broker", this.name, data);

                processEpub(data);

                data = broker.get();
            }

            log.debug("Comsumer " + this.name + " finished its job; terminating.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void processEpub(File data) {
        try {
            Book book = bookProducer.produce(data);

            bookService.save(book);



//            EpubReader epubReader = new EpubReader();
//            Book book = epubReader.readEpub(new FileInputStream(data));
//
//            log.debug("**************** " + data + " **************************");
//            log.debug("Authors: " + book.getMetadata().getAuthors());
//            log.debug("Dates: " + book.getMetadata().getDates());
//            log.debug("Descriptions: " + book.getMetadata().getDescriptions());
//            log.debug("First title: " + book.getMetadata().getFirstTitle());
//            log.debug("Format: " + book.getMetadata().getFormat());
//            log.debug("Identifiers: " + book.getMetadata().getIdentifiers());
//            log.debug("Language: " + book.getMetadata().getLanguage());
//            log.debug("Other properties: " + book.getMetadata().getOtherProperties());
//            log.debug("Publishers: " + book.getMetadata().getPublishers());
//            log.debug("Rights: " + book.getMetadata().getRights());
//            log.debug("Subjects: " + book.getMetadata().getSubjects());
//            log.debug("Types: " + book.getMetadata().getTypes());
//            log.debug("Titles: " + book.getMetadata().getTitles());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
