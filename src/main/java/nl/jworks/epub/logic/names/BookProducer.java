package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.strategy.author.*;
import nl.jworks.epub.logic.strategy.title.TitleScorer;

import java.io.File;
import java.io.IOException;

public class BookProducer {


    public Book produce(File input) throws IOException {

        Book book = new Book();

        book.setAuthors(new AuthorScorer().determineBestScore(input).getValue());
        book.setSource("import");
        book.setTitle(new TitleScorer().determineBestScore(input).getValue());



        return book;

    }

}
