package nl.jworks.epub.mongodb.spring;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import nl.jworks.epub.mongodb.spring.repository.BinaryRepository;
import nl.jworks.epub.mongodb.spring.repository.BookRepository;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataMongoConfig.class)
public class SpringDataMongoTest extends BookSupport {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BinaryRepository binaryRepository;

    private Book createBook() {
        return createBook("spring");
    }


    @Before
    public void setUp() throws Exception {
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            bookRepository.delete(book.id);
        }
    }

    @Test
    public void insertBookWithOneAuthor() {
        Book book = createBook();

        Book savedBook = bookRepository.save(book);

        Book result = bookRepository.findOne(savedBook.id);
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 1);
    }

    @Test
    public void insertBookWithManyAuthors() throws Exception {

        Book book = createBook();
        book.addAuthor(new Author("Jeff", "Brown"));
        book.addAuthor(new Author("Erik", "Pragt"));

        Book savedBook = bookRepository.save(book);

        Book result = bookRepository.findOne(savedBook.id);
        assertEquals(book, result);
        assertEquals(result.getAuthors().size(), 3);
    }


    @Test
    public void listAllBooks() throws Exception {

        Book book1 = createBookWithTitle("spring", "Grails programming 101");
        Book book2 = createBookWithTitle("spring", "Getting started with Grails");
        Book book3 = createBookWithTitle("spring", "Grails in Action");

        bookRepository.save(Arrays.asList(book1, book2, book3));

        long count = bookRepository.count();

        assertEquals(3, count);

        List<Book> result = bookRepository.findAll();

        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
        assertEquals(book3, result.get(2));
    }

    @Test
    public void findBooksByTitle() {
        assertNotNull(bookRepository.findByTitle("Getting started with Grails"));
    }

    @Test
    public void findBooksByAuthorLastname() {
        saveDefaultBook();

        List<Book> foundBooks = bookRepository.findAllByAuthorLastname("Rocher");

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksByAuthorFirstnameAndLastname() {
        saveDefaultBook();

        List<Book> foundBooks = bookRepository.findAllByAuthorFirstnameAndLastname("Graeme", "Rocher");

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Graeme", actual.getFirstName());
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksByAuthor() {
        saveDefaultBook();

        List<Book> foundBooks = bookRepository.findAllByAuthors(new Author("Graeme", "Rocher"));

        Author actual = foundBooks.get(0).getAuthors().get(0);
        assertEquals("Graeme", actual.getFirstName());
        assertEquals("Rocher", actual.getLastName());
    }

    @Test
    public void findBooksAfter1980WithLessThan400Pages() {
        Book book = saveDefaultBook();

        List<Book> books = bookRepository.findAllByPublicationDateAfterAndNumberOfPagesLessThan(createDate("01-01-1980"), 400);

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void insertBookWithBinaryEpub() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/alice-in-wonderland.epub");

        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setEpub(binary);

        binaryRepository.save(binary);
        Book save = bookRepository.save(book);

        Book foundBook = bookRepository.findOne(save.id);

        File outputFile = File.createTempFile("output", ".epub");
        FileUtils.writeByteArrayToFile(outputFile, foundBook.getEpub().getContents());

        // read epub file
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epub = epubReader.readEpub(new FileInputStream(outputFile));

        // print the first title
        List<String> titles = epub.getMetadata().getTitles();

        assertEquals("Alice's Adventures in Wonderland / Illustrated by Arthur Rackham. With a Proem by Austin Dobson", titles.get(0));
    }

    @Test
    public void insertBookWithCover() throws Exception {
        Book book = createBook();

        File file = new File("src/test/resources/32x32.jpg");
        long originalLength = file.length();

        Binary binary = new Binary(FileUtils.readFileToByteArray(file));
        book.setCover(binary);

        binaryRepository.save(binary);
        Book save = bookRepository.save(book);

        Book foundBook = bookRepository.findOne(save.id);

        File outputFile = File.createTempFile("output", ".jpg");
        FileUtils.writeByteArrayToFile(outputFile, foundBook.getCover().getContents());

        long copyLength = file.length();

        assertEquals(originalLength, copyLength);
    }



    private Book saveDefaultBook() {
        Book book = createBook();
        return bookRepository.save(book);
    }


}
