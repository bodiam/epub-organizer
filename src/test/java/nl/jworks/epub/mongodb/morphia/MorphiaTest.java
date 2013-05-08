package nl.jworks.epub.mongodb.morphia;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.domain.Tag;
import nl.jworks.epub.mongodb.common.BookSupport;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MorphiaTest extends BookSupport {

    private Datastore ds;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("nl.jworks.epub.domain.domain");

        ds = morphia.createDatastore("epub");
        ds.ensureIndexes();
        ds.ensureCaps();

        ds.delete(ds.createQuery(Book.class));

        Book found = ds.find(Book.class).field("title").equal("Getting started with Grails").get(); //get the first one where name = "someName
        assertNull(found);

    }

    @Test
    public void insertBookWithOneAuthor() throws Exception {

        Book book = createBook();

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 1);
    }

    @Test
    public void insertBookWithManyAuthors() throws Exception {

        Book book = createBook();
        book.addAuthor(new Author("Jeff", "Brown"));
        book.addAuthor(new Author("Erik", "Pragt"));

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 3);
    }

    @Test
    public void listAllBooks() throws Exception {

        Book book1 = createBookWithTitle("Grails programming 101");
        Book book2 = createBookWithTitle("Getting started with Grails");
        Book book3 = createBookWithTitle("Grails in Action");

        ds.save(book1, book2, book3);

        long count = ds.getCount(Book.class);

        assertEquals(3, count);

        List<Book> result = ds.find(Book.class).asList();

        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
        assertEquals(book3, result.get(2));
    }

    @Test
    public void findBooksByTitle() throws Exception {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("title").equal("Getting started with Grails").get());

    }

    @Test
    public void findBooksByAuthorLastname() {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("authors.lastName").equal("Rocher").get());
    }

    @Test
    public void findBooksByAuthorFirstnameAndLastname() {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("authors.firstName").equal("Graeme").field("authors.lastName").equal("Rocher").get());
    }

    @Test
    public void findBooksByAuthor() {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("authors").equal(new Author("Graeme", "Rocher")).get());
    }

    @Test
    public void findBooksAfter1980WithLessThan400Pages() {
        saveDefaultBook();

        Query q1 = ds.createQuery(Book.class).filter("publicationDate >", createDate("01-01-1980")).filter("numberOfPages <", 400);
        Query q2 = ds.find(Book.class).field("publicationDate").greaterThan(createDate("01-01-1980")).field("numberOfPages").lessThan(400);

//        String s1 = ((QueryImpl) q1).getQueryObject().toString();

        assertNotNull(q1.get());
        assertNotNull(q2.get());
    }

    @Test
    public void insertBookWithBinaryEpub() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/alice-in-wonderland.epub");

        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setEpub(binary);

        ds.save(binary, book);

        Book foundBook = ds.find(Book.class).get();

        FileUtils.writeByteArrayToFile(new File("output.epub"), foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epub = epubReader.readEpub(new FileInputStream(new File("output.epub")));

        // print the first title
        List<String> titles = epub.getMetadata().getTitles();

        assertEquals("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson", titles.get(0));
    }

    @Test
    public void insertBookWithCover() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/32x32.jpg");
        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setCover(binary);

        ds.save(binary, book);
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

        ds.save(epub, cover, book);

        Book foundBook = ds.find(Book.class).get();

        FileUtils.writeByteArrayToFile(new File("output.epub"), foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book result = epubReader.readEpub(new FileInputStream(new File("output.epub")));

        // print the first title
        List<String> titles = result.getMetadata().getTitles();

        assertEquals("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson", titles.get(0));
    }

    @Test
    public void removeBook() throws Exception {
        Book book = createBook();

        ds.save(book);
        Book result = ds.find(Book.class).get();
        assertNotNull(result);

        ds.delete(book);

        assertNull(ds.find(Book.class).get());
    }



    @Test
    public void insertBookWithManyTags() throws Exception {

        Book book = createBook();
        book.addTag(new Tag("groovy"));
        book.addTag(new Tag("cool"));
        book.addTag(new Tag("manning"));
        book.addTag(new Tag("programming"));

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertEquals(book, result);
        assertEquals(result.getTags().size(), 5);
    }

    @Test
    public void findByTitle() {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("title").equal("Getting started with Grails").get());
    }

    @Test
    public void findByAuthor() {
        saveDefaultBook();

        assertNotNull(ds.find(Book.class).field("authors.lastName").equal("Rocher").get());
    }

    @Test
    public void findByAuthorNoAuthorFound() {
        saveDefaultBook();

        assertNull(ds.find(Book.class).field("authors.lastName").equal("Pietje puk").get());
    }


    @Test
    public void insertBook() throws Exception {
        Book book = createBook();

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertEquals(book, result);
    }


    private void saveDefaultBook() {
        Book book = createBook();
        ds.save(book);
    }
}
