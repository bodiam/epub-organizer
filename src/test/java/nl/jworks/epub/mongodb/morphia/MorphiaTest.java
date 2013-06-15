package nl.jworks.epub.mongodb.morphia;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.domain.Tag;
import nl.jworks.epub.mongodb.common.BookSupport;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MorphiaTest extends BookSupport {

    private Datastore ds;

    private Book createBook() {
        return createBook("morphia");
    }

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
        assertThat(found, is(nullValue()));

    }

    @Test
    public void insertBookWithOneAuthor() throws Exception {

        Book book = createBook();

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertThat(result, is(book));
        assertThat(result.getAuthors(), hasSize(1));
    }

    @Test
    public void insertBookWithManyAuthors() throws Exception {

        Book book = createBook();
        book.addAuthor(new Author("Jeff", "Brown"));
        book.addAuthor(new Author("Erik", "Pragt"));

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertThat(result, is(book));
        assertThat(result.getAuthors(), hasSize(3));
    }

    @Test
    public void listAllBooks() throws Exception {

        Book book1 = createBookWithTitle("morphia", "Grails programming 101");
        Book book2 = createBookWithTitle("morphia", "Getting started with Grails");
        Book book3 = createBookWithTitle("morphia", "Grails in Action");

        ds.save(book1, book2, book3);

        List<Book> result = ds.find(Book.class).asList();

        assertThat(result, hasItems(book1, book2, book3));
    }

    @Test
    public void findBooksByTitle() throws Exception {
        saveDefaultBook();

        Book book = ds.find(Book.class).field("title").equal("Getting started with Grails").get();

        Author actual = book.getAuthors().get(0);

        assertThat(actual.getLastName(), is("Rocher"));

    }

    @Test
    public void findBooksByAuthorLastname() {
        saveDefaultBook();

        assertThat(ds.find(Book.class).field("authors.lastName").equal("Rocher").get(), is(notNullValue()));
    }

    @Test
    public void findBooksByAuthorFirstnameAndLastname() {
        saveDefaultBook();

        assertThat(ds.find(Book.class).field("authors.firstName").equal("Graeme").field("authors.lastName").equal("Rocher").get(), is(notNullValue()));
    }

    @Test
    public void findBooksByAuthor() {
        saveDefaultBook();

        Book book = ds.find(Book.class).field("authors").equal(new Author("Graeme", "Rocher")).get();
        assertThat(book, is(notNullValue()));

        Author author = book.getAuthors().get(0);
        assertThat(author.getFirstName(), is("Graeme"));
        assertThat(author.getLastName(), is("Rocher"));
    }

    @Test
    public void findBooksAfter1980WithLessThan400Pages() {
        saveDefaultBook();

        Query q1 = ds.createQuery(Book.class).filter("publicationDate >", createDate("01-01-1980")).filter("numberOfPages <", 400);
        Query q2 = ds.find(Book.class).field("publicationDate").greaterThan(createDate("01-01-1980")).field("numberOfPages").lessThan(400);

        // debug
//        String s1 = ((QueryImpl) q1).getQueryObject().toString();

        assertThat(q1.get(), is(notNullValue()));
        assertThat(q2.get(), is(notNullValue()));
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

        assertThat(titles, hasItem("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson"));
    }

    @Test
    public void insertBookWithCover() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/32x32.jpg");
        long originalLength = file.length();
        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setCover(binary);

        ds.save(binary, book);

        Book foundBook = ds.find(Book.class).get();

        File outputFile = File.createTempFile("output", ".jpg");
        FileUtils.writeByteArrayToFile(outputFile, foundBook.getCover().getContents());

        long copyLength = file.length();

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

        ds.save(epub, cover, book);

        Book foundBook = ds.find(Book.class).get();

        FileUtils.writeByteArrayToFile(new File("output.epub"), foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book result = epubReader.readEpub(new FileInputStream(new File("output.epub")));

        // print the first title
        List<String> titles = result.getMetadata().getTitles();

        assertThat(titles, hasItem("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson"));
    }

    @Test
    public void addCoverToBook() throws Exception {
        saveDefaultBook();
        Book book = ds.find(Book.class).get();

        assertThat(book.getCover(), is(nullValue()));

        File coverFile = new File("src/test/resources/32x32.jpg");
        Binary cover = new Binary(FileUtils.readFileToByteArray(coverFile));
        book.setCover(cover);

        ds.save(cover, book);

        // https://code.google.com/p/morphia/wiki/Updating
        UpdateOperations<Book> updateOperations = ds.createUpdateOperations(Book.class).set("cover", cover);
        ds.update(ds.find(Book.class), updateOperations);
        Book savedAgain = ds.find(Book.class).get();

        assertThat(savedAgain.getCover(), is(notNullValue()));
    }

    @Test
    public void removeBook() throws Exception {
        Book book = createBook();

        ObjectId id = (ObjectId)ds.save(book).getId();

        // There's some hacking going one: when using Morphia, these queries don't work
        // when the id is a String. Making the id an ObjectId
//        Book entity = ds.get(Book.class, id);

        assertThat(ds.find(Book.class).get(), is(notNullValue()));

        ds.delete(Book.class, id);

        assertThat(ds.find(Book.class).get(), is(nullValue()));
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
        assertThat(result, is(book));
        assertThat(result.getTags(), hasSize(5));
    }

    @Test
    public void findByTitle() {
        saveDefaultBook();

        assertThat(ds.find(Book.class).field("title").equal("Getting started with Grails").get(), is(notNullValue()));
    }

    @Test
    public void findByAuthor() {
        saveDefaultBook();

        assertThat(ds.find(Book.class).field("authors.lastName").equal("Rocher").get(), is(notNullValue()));
    }

    @Test
    public void findByAuthorNoAuthorFound() {
        saveDefaultBook();

        assertThat(ds.find(Book.class).field("authors.lastName").equal("Pietje puk").get(), is(nullValue()));
    }


    @Test
    public void insertBook() throws Exception {
        Book book = createBook();

        ds.save(book);

        Book result = ds.find(Book.class).get();
        assertThat(result.getCover(), is(book.getCover()));
        assertThat(result, is(book));
    }


    private void saveDefaultBook() {
        Book book = createBook();
        ds.save(book);
    }
}
