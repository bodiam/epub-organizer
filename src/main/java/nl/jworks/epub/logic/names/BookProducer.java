package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.strategy.BookContext;
import nl.jworks.epub.logic.strategy.author.AuthorScorer;
import nl.jworks.epub.logic.strategy.isbn10.Isbn10Scorerer;
import nl.jworks.epub.logic.strategy.isbn13.Isbn13Scorerer;
import nl.jworks.epub.logic.strategy.language.LanguageScorer;
import nl.jworks.epub.logic.strategy.title.TitleScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BookProducer {

    private static Logger log = LoggerFactory.getLogger(BookProducer.class);

    public Book produce(BookContext context) throws IOException {

        Book book = new Book();

        book.setAuthors(new AuthorScorer().determineBestScore(context).getValue());
        book.setSource("import");
        book.setTitle(new TitleScorer().determineBestScore(context).getValue());
        book.setLanguage((new LanguageScorer().determineBestScore(context).getValue()));
        book.setIsbn10(new Isbn10Scorerer().determineBestScore(context).getValue());
        book.setIsbn13(new Isbn13Scorerer().determineBestScore(context).getValue());

        log.info("Produced book {}", book);

        return book;

    }

}
