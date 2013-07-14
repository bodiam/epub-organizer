package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.logic.strategy.BookImportContext;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BookProducerTest {

    @Test
    public void testEnrichBooks() throws Exception {

        BookImportContext bookImportContext = new BookImportContext(new File("src/test/resources/epubs/W. H. Davenport Adams - Some heroes of Travel.epub"));

        Book book = new BookProducer().produce(bookImportContext);

        assertThat(book.getFirstAuthor(), is(new Author("Davenport", "Adams W. H.")));

    }
}

