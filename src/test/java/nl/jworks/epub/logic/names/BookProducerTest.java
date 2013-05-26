package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class BookProducerTest {


    @Test
    public void testEnrichBooks() throws Exception {

        Book book = new BookProducer().produce(new File("src/test/resources/epubs/W. H. Davenport Adams - Some heroes of Travel.epub"));

        assertEquals(new Author("Davenport", "Adams W. H."), book.getFirstAuthor());

    }
}

