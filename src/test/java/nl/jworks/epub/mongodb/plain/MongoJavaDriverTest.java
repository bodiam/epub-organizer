package nl.jworks.epub.mongodb.plain;

import com.mongodb.*;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MongoJavaDriverTest extends BookSupport {

    private Book createBook() {
        return createBook("plain");
    }

    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase("epub");
    }

    @Test
    public void insertBookWithOneAuthor() throws Exception {
        Book book = createBook();

        MongoClient mongoClient = new MongoClient();
        mongoClient.setWriteConcern(WriteConcern.JOURNALED);

        DB db = mongoClient.getDB("epub");
        DBCollection books = db.getCollection("books");

        BasicDBList authors = new BasicDBList();
        authors.add(new BasicDBObject("firstName", "Graeme")
                .append("lastName", "Rocher")
        );

        BasicDBList tags = new BasicDBList();
        tags.add(new BasicDBObject("value", "Grails"));

        BasicDBObject doc = new BasicDBObject("source", book.getSource())
                .append("title", book.getTitle())
                .append("language", book.getLanguage())
                .append("isbn", book.getIsbn())
                .append("summary", book.getSummary())
                .append("publisher", book.getPublisher())
                .append("publicationDate", book.getPublicationDate())
                .append("numberOfPages", book.getNumberOfPages())
                .append("fileSizeInKb", book.getFileSizeInKb())
                .append("authors", authors)
                .append("tags", tags);

        books.insert(doc);

        DBObject myDoc = books.findOne();

        assertEquals(book.getTitle(), myDoc.get("title"));
    }
}
