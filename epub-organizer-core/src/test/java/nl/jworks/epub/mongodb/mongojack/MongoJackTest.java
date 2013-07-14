package nl.jworks.epub.mongodb.mongojack;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MongoJackTest extends BookSupport {

    private DBCollection booksCollection;
    private JacksonDBCollection<Book,ObjectId> books;
    private JacksonDBCollection<Binary,ObjectId> binaries;

    private Book createBook() {
        return createBook("mongojack");
    }

    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase("epub");
        mongoClient.setWriteConcern(WriteConcern.JOURNALED);

        DB db = mongoClient.getDB("epub");
        booksCollection = db.getCollection("books");
        DBCollection binariesCollection = db.getCollection("binaries");

        books = JacksonDBCollection.wrap(booksCollection, Book.class, ObjectId.class);
        binaries = JacksonDBCollection.wrap(binariesCollection, Binary.class, ObjectId.class);
    }


    @Test
    public void insertBookWithOneAuthor() throws Exception {
        Book book = createBook();

        WriteResult<Book,ObjectId> writeResult = books.insert(book);

        ObjectId id = writeResult.getSavedId();

        Book result = books.findOneById(id);

        assertThat(result, is(book));
        assertThat(result.getAuthors(), hasSize(1));
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
        assertThat(result, is(book));
        assertThat(result.getAuthors(), hasSize(3));
    }

    @Test
    public void listAllBooks() throws Exception {

        Book book1 = createBookWithTitle("mongojack", "Grails programming 101");
        Book book2 = createBookWithTitle("mongojack", "Getting started with Grails");
        Book book3 = createBookWithTitle("mongojack", "Grails in Action");

        books.insert(book1, book2, book3);

        List<Book> result = books.find().toArray();

        assertThat(result, hasItems(book1, book2, book3));
    }

    @Test
    public void findBooksByTitle() {
        saveDefaultBook();

        assertThat(books.findOne(DBQuery.is("title", "Getting started with Grails")).getTitle(), is("Getting started with Grails"));
    }

    @Test
    public void findBooksByAuthorLastname() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors.lastName", "Rocher")).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);

        assertThat(actual.getLastName(), is("Rocher"));
    }

    @Test
    public void findBooksByAuthorFirstnameAndLastname() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors.firstName", "Graeme").is("authors.lastName", "Rocher")).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);

        assertThat(actual.getFirstName(), is("Graeme"));
        assertThat(actual.getLastName(), is("Rocher"));
    }

    @Test
    public void findBooksByAuthor() {
        saveDefaultBook();

        List<Book> foundBooks = books.find(DBQuery.is("authors", Arrays.asList(new Author("Graeme", "Rocher")))).toArray();

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertThat(actual.getFirstName(), is("Graeme"));
        assertThat(actual.getLastName(), is("Rocher"));
    }

    @Test
    public void findBooksAfter1980WithLessThan400Pages() {
        Book book = saveDefaultBook();

        List<Book> books = this.books.find(DBQuery.greaterThan("publicationDate", createDate("01-01-1980")).lessThan("numberOfPages", 400)).toArray();

        assertThat(books, hasItem(book));
    }

    @Test
    public void insertBookWithBinaryEpub() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/alice-in-wonderland.epub");

        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setEpub(binary);

        binaries.insert(binary);
        WriteResult<Book, ObjectId> writeResult = books.insert(book);

        Book foundBook = books.findOneById(writeResult.getSavedId());

        File outputFile = File.createTempFile("output", ".epub");
        FileUtils.writeByteArrayToFile(outputFile, foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epub = epubReader.readEpub(new FileInputStream(outputFile));

        // print the first title
        List<String> titles = epub.getMetadata().getTitles();

        assertThat(titles, hasItem("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson"));
    }


    @Test
    public void insertBookWithCover() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/32x32.jpg");
        long originalLength = file.length();

        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setCover(binary);

        binaries.insert(binary);
        WriteResult<Book, ObjectId> writeResult = books.insert(book);

        Book foundBook = books.findOneById(writeResult.getSavedId());

        File outputFile = File.createTempFile("output", ".jpg");
        FileUtils.writeByteArrayToFile(outputFile, foundBook.getCover().getContents());

        long copyLength = file.length();

//        BookViewer.view(foundBook);
        assertThat(copyLength, is(originalLength));
    }

    @Test
    public void insertBookWithBinaryEpubAndCover() throws Exception {
        Book book = createBook();

        File epubFile = new File("src/test/resources/alice-in-wonderland.epub");
        Binary epub = new Binary(FileUtils.readFileToByteArray(epubFile));
        book.setEpub(epub);

        File coverFile = new File("src/test/resources/32x32.jpg");
        Binary cover = new Binary(FileUtils.readFileToByteArray(coverFile));
        book.setCover(cover);

        binaries.insert(cover, epub);

        WriteResult<Book, ObjectId> writeResult = books.insert(book);

        Book foundBook = books.findOneById(writeResult.getSavedId());

        FileUtils.writeByteArrayToFile(new File("output.epub"), foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book result = epubReader.readEpub(new FileInputStream(new File("output.epub")));

        // print the first title
        List<String> titles = result.getMetadata().getTitles();

        assertThat(titles, hasItem("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson"));
    }

    /*
    @Test
    public void addCoverToBook() throws Exception {
        Book book = saveDefaultBook();

        assertNull(book.getCover());

        Book foundBook = books.findOneById(book.id);
        File coverFile = new File("src/test/resources/32x32.jpg");
        Binary cover = new Binary(FileUtils.readFileToByteArray(coverFile));
        foundBook.setCover(cover);

        books.findAndModify();

        binaries.save(cover);
        Book savedAgain = books.insert(foundBook).getSavedObject();

        assertNotNull(savedAgain.getCover());
    }*/



    private Book saveDefaultBook() {
        Book book = createBook();
        return books.insert(book).getSavedObject();
    }

}
