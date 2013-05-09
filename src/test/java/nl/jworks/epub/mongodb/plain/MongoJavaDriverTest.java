package nl.jworks.epub.mongodb.plain;

import com.mongodb.*;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MongoJavaDriverTest extends BookSupport {

    private Book createBook() {
        return createBook("plain");
    }

    @Test
    public void insertBookWithOneAuthor() throws Exception {
        Book book = createBook();

        MongoClient mongoClient = new MongoClient();
        mongoClient.setWriteConcern(WriteConcern.JOURNALED);

        DB db = mongoClient.getDB("epub");
        DBCollection books = db.getCollection("books");

        BasicDBObject doc = new BasicDBObject("source", book.getSource())
                .append("title", book.getTitle())
                .append("language", book.getLanguage())
                .append("isbn", book.getIsbn())
                .append("summary", book.getSummary())
                .append("publisher", book.getPublisher())
                .append("publicationDate", book.getPublicationDate())
                .append("numberOfPages", book.getNumberOfPages())
                .append("fileSizeInKb", book.getFileSizeInKb())
                .append("authors", new BasicDBObject("firstName", "Graeme")
                        .append("lastName", "Rocher")
                )
                .append("tags", new BasicDBObject("firstName", "Graeme")
                        .append("lastName", "Rocher")
                );

        books.insert(doc);

        DBObject myDoc = books.findOne();

        assertEquals(book.getTitle(), myDoc.get("title"));
    }
}
