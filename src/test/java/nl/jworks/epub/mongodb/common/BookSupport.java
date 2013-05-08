package nl.jworks.epub.mongodb.common;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.domain.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookSupport {


    protected Book createBookWithTitle(String title) {
        Book book = createBook();
        book.setTitle(title);
        return book;
    }

    protected Book createBook() {
        Book book = new Book();
        book.setSource("spring");
        book.setTitle("Getting started with Grails");
        book.setLanguage("nl");
        book.setIsbn("1430243775");
        book.setSummary(getSummary());
        book.setPublisher("Manning");
        book.setPublicationDate(createDate("01-01-2006"));

        book.setNumberOfPages(342);
        book.setFileSizeInKb(5682);

        book.addAuthor(new Author("Graeme", "Rocher"));
        book.addTag(new Tag("grails"));
        return book;
    }


    protected String getSummary() {
        return "Grails is a full stack framework which aims to greatly simplify the task of building serious web applications for the JVM. The concepts within Grails, like interceptors, tag libs, and Groovy Server Pages (GSP), make those in the Java community feel right at home.\n" +
                "\n" +
                "Grails’ foundation is on solid open source technologies such as Spring, Hibernate, and SiteMesh, which gives it even more potential in the Java space: Spring provides powerful inversion of control and MVC, Hibernate brings a stable, mature object relational mapping technology with the ability to integrate with legacy systems, and SiteMesh handles flexible layout control and page decoration.\n" +
                "\n" +
                "Grails complements these with additional features that take advantage of the coding–by–convention paradigm such as dynamic tag libraries, Grails object relational mapping, Groovy Server Pages, and scaffolding.\n" +
                "\n" +
                "Graeme Rocher, Grails lead and founder, and Jeff Brown bring you completely up–to–date with their authoritative and fully comprehensive guide to the Grails 2 framework. You’ll get to know all the core features, services, and Grails extensions via plug–ins, and understand the roles that Groovy and Grails are playing in the changing Web.";
    }

    protected Date createDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
