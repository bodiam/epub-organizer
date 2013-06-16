package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.author.AuthorScorer;
import nl.jworks.epub.logic.strategy.cover.CoverScorer;
import nl.jworks.epub.logic.strategy.isbn10.Isbn10Scorer;
import nl.jworks.epub.logic.strategy.isbn13.Isbn13Scorer;
import nl.jworks.epub.logic.strategy.language.LanguageScorer;
import nl.jworks.epub.logic.strategy.publicationdate.PublicationDateScorer;
import nl.jworks.epub.logic.strategy.publisher.PublisherScorer;
import nl.jworks.epub.logic.strategy.summary.SummaryScorer;
import nl.jworks.epub.logic.strategy.tags.TagsScorer;
import nl.jworks.epub.logic.strategy.title.TitleScorer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BookProducer {

    private static Logger log = LoggerFactory.getLogger(BookProducer.class);

    public Book produce(BookImportContext context) throws IOException {

        Book book = new Book();

        book.setAuthors(new AuthorScorer().determineBestScore(context).getValue());
        book.setSource("import");
        book.setTitle(new TitleScorer().determineBestScore(context).getValue());
        book.setLanguage((new LanguageScorer().determineBestScore(context).getValue()));
        book.setIsbn10(new Isbn10Scorer().determineBestScore(context).getValue());
        book.setIsbn13(new Isbn13Scorer().determineBestScore(context).getValue());
        book.setSummary(new SummaryScorer().determineBestScore(context).getValue());
        book.setPublisher(new PublisherScorer().determineBestScore(context).getValue());
        book.setPublicationDate(new PublicationDateScorer().determineBestScore(context).getValue());

        book.setTags(new TagsScorer().determineBestScore(context).getValue());

        // private String dewey; // ddc

        Binary epub = getBinaryFromFile(context);
        book.setEpub(epub);
        book.setFileSizeInKb(epub.getContents().length / 1024);

        book.setCover(new CoverScorer().determineBestScore(context).getValue());

//        private int numberOfPages;

        log.info("Produced book {}", book);

        return book;

    }

    private Binary getBinaryFromFile(BookImportContext context) throws IOException {
        return new Binary(FileUtils.readFileToByteArray(context.getFile()));
    }

}
