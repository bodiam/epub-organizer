package nl.jworks.epub.mongodb.spring;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.common.BookSupport;
import nl.jworks.epub.mongodb.spring.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataMongoConfig.class)
public class SpringDataMongoTest extends BookSupport {

    //    private AnnotationConfigApplicationContext ctx;
    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
//        ctx = new AnnotationConfigApplicationContext(SpringDataMongoTest.class.getPackage().getName());
//        bookRepository = ctx.getBean(BookRepository.class);

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

        Book book1 = createBookWithTitle("Grails programming 101");
        Book book2 = createBookWithTitle("Getting started with Grails");
        Book book3 = createBookWithTitle("Grails in Action");

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


    private Book saveDefaultBook() {
        Book book = createBook();
        return bookRepository.save(book);
    }


}
