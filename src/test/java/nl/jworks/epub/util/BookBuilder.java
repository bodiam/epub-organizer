package nl.jworks.epub.util;

import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BookBuilder {

    private Book book = new Book();

    public Book build() {
        return book;
    }

    private File writeFile() {
        File file = null;

        try {
            file = File.createTempFile("book-" + book.getTitle() + String.valueOf(System.currentTimeMillis()), ".epub");

            // Create EpubWriter
            EpubWriter epubWriter = new EpubWriter();

            // Write the Book as Epub
            epubWriter.write(book, new FileOutputStream(file));

            return file;
        } catch (IOException e) {
            throw new RuntimeException("Could not write file " + file, e);
        }
    }

    public BookImportContext buildContext() {
        File output = writeFile();

        System.out.println(output.getAbsolutePath());
        return new BookImportContext(output);
    }

    public BookBuilder title(String title) {
        book.getMetadata().addTitle(title);
        return this;
    }

    public BookBuilder author(String firstName, String lastName) {
        book.getMetadata().addAuthor(new Author(firstName, lastName));
        return this;
    }

    public BookBuilder cover(String location) {
        try {
            String href = getHref(location);
            book.setCoverImage(new Resource(BookBuilder.class.getResourceAsStream(location), href));
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Could not load cover from location " + location, e);
        }
    }

    public BookBuilder tags(String... tags) {
        book.getMetadata().setSubjects(Arrays.asList(tags));
        return this;
    }

    public BookBuilder isbn(String isbn) {

        List<Identifier> identifiers = book.getMetadata().getIdentifiers();
        identifiers.add(new Identifier(Identifier.Scheme.ISBN, isbn));
        book.getMetadata().setIdentifiers(identifiers);

        return this;
    }


    public BookBuilder chapter(String title, String location) {
        try {
            String href = getHref(location);
            book.addSection(title, new Resource(BookBuilder.class.getResourceAsStream(location), href));
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Could not load chapter from location" + location);
        }
    }

    // Add Chapter 2
//    TOCReference chapter2 = book.addSection("Second Chapter", new Resource(Simple1.class.getResourceAsStream("/book1/chapter2.html"), "chapter2.html"));

// Add image used by Chapter 2
//    book.getResources().add(new Resource(Simple1.class.getResourceAsStream("/book1/flowers_320x240.jpg"), "flowers.jpg"));

// Add Chapter2, Section 1
//    book.addSection(chapter2, "Chapter 2, section 1", new Resource(Simple1.class.getResourceAsStream("/book1/chapter2_1.html"), "chapter2_1.html"));

// Add Chapter 3
//    book.addSection("Conclusion", new Resource(Simple1.class.getResourceAsStream("/book1/chapter3.html"), "chapter3.html"));


    public BookBuilder css(String location) {
        try {
            String href = getHref(location);
            book.getResources().add(new Resource(BookBuilder.class.getResourceAsStream(location), href));
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Could not load css from location" + location);
        }
    }


    private String getHref(String location) {
        return StringUtils.substring(location, StringUtils.lastIndexOf(location, "/"));
    }

}