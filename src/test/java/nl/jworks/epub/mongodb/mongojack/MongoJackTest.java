package nl.jworks.epub.mongodb.mongojack;

import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class MongoJackTest extends BookSupport {

    private DBCollection booksCollection;
    private JacksonDBCollection<Book,ObjectId> books;

    private Book createBook() {
        return createBook("mongojack");
    }

    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase("epub");
        mongoClient.setWriteConcern(WriteConcern.JOURNALED);

        DB db = mongoClient.getDB("epub");
        booksCollection = db.getCollection("booksCollection");

        books = JacksonDBCollection.wrap(booksCollection, Book.class, ObjectId.class);
        
    }


    @Test
    public void insertBookWithOneAuthor() throws Exception {
        Book book = createBook();

        WriteResult<Book,ObjectId> writeResult = books.insert(book);

        ObjectId id = writeResult.getSavedId();

        Book result = books.findOneById(id);
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 1);
    }

    @Test
    public void insertBookWithManyAuthors() throws Exception {
        Book book = createBook();
        book.addAuthor(new Author("Jeff", "Brown"));
        book.addAuthor(new Author("Erik", "Pragt"));

        JacksonDBCollection<Book, ObjectId> books = JacksonDBCollection.wrap(booksCollection, Book.class, ObjectId.class);
        WriteResult<Book,ObjectId> writeResult = books.insert(book);

        ObjectId id = writeResult.getSavedId();

        Book result = books.findOneById(id);
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 3);
    }

    @Test
    public void listAllBooks() throws Exception {

        Book book1 = createBookWithTitle("mongojack", "Grails programming 101");
        Book book2 = createBookWithTitle("mongojack", "Getting started with Grails");
        Book book3 = createBookWithTitle("mongojack", "Grails in Action");

        books.insert(book1, book2, book3);

        long count = books.count();

        assertEquals(3, count);

        List<Book> result = books.find().toArray();

        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
        assertEquals(book3, result.get(2));
    }

    @Test
    public void findBooksByTitle() {
        saveDefaultBook();

        assertEquals("Getting started with Grails", books.findOne(DBQuery.is("title", "Getting started with Grails")).getTitle());
    }

    @Test
    public void findBooksByAuthorLastname() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors.lastName", "Rocher")).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksByAuthorFirstnameAndLastname() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors.firstName", "Graeme").is("authors.lastName", "Rocher")).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Graeme", actual.getFirstName());
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksByAuthor() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors", Arrays.asList(new Author("Graeme", "Rocher")))).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Graeme", actual.getFirstName());
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksAfter1980WithLessThan400Pages() {
        Book book = saveDefaultBook();

        List<Book> books = this.books.find(DBQuery.greaterThan("publicationDate", createDate("01-01-1980")).lessThan("numberOfPages", 400)).toArray();

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }




    private Book saveDefaultBook() {
        Book book = createBook();
        return books.insert(book).getSavedObject();
    }

}
