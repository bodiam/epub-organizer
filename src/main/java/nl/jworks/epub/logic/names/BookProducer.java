package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.strategy.author.*;
import nl.jworks.epub.logic.strategy.title.TitleScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class BookProducer {

    private static Logger log = LoggerFactory.getLogger(BookProducer.class);

    public Book produce(File input) throws IOException {

        Book book = new Book();

        book.setAuthors(new AuthorScorer().determineBestScore(input).getValue());
        book.setSource("import");
        book.setTitle(new TitleScorer().determineBestScore(input).getValue());


        log.info("Produced book {}", book);

        return book;

    }

}
