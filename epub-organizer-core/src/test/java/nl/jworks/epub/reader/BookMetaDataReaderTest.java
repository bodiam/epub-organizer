package nl.jworks.epub.reader;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BookMetaDataReaderTest {


    @Test
    public void readMetaData() throws Exception {

//        File file = new File("/Users/erikp/Desktop/gutenberg/snowy.arsc.alaska.edu/gutenberg/cache/generated/107/pg107-images.epub");
//        File file = new File("/Users/erikp/Desktop/gutenberg/snowy.arsc.alaska.edu/gutenberg/cache/generated/42690/pg42690-images.epub");
        String location = "src/test/resources/epubs/The Friendship Club of Madison - Friendship Club Cook Book.epub";
        File file = new File(location);

        EpubReader epubReader = new EpubReader();
        Book book = epubReader.readEpub(new FileInputStream(file));
        Metadata metadata = book.getMetadata();

        System.out.println("Location            : " + location);
        System.out.println("First Title         : " + metadata.getFirstTitle());
        System.out.println("Title               : " + book.getTitle());
        System.out.println("Author              : " + metadata.getAuthors());
        System.out.println("Language            : " + metadata.getLanguage());
        System.out.println("Isbn10              : " + metadata.getIdentifiers());
        System.out.println("Isbn13              : " + metadata.getIdentifiers());
        System.out.println("Summary             : " + metadata.getDescriptions());
        System.out.println("Publisher           : " + metadata.getPublishers());
        System.out.println("Dates               :");
        List<Date> dates = metadata.getDates();
        for (Date date : dates) {
            System.out.println("  " + date.getEvent() + " = " + date.getValue());
        }

        System.out.println("Subject             : " + metadata.getSubjects());
        System.out.println("Number of pages     : " + "unknown");
        System.out.println("Filesize in kb      : " + (file.length() / 1024));

        Spine spine = book.getSpine();
        System.out.println("Spine size          : " + spine.size());
        System.out.println("Content             : " + printContent(book.getContents()));

        printPerMediaType(book.getResources().getAll());
    }

    private String printContent(List<Resource> contents) throws Exception {
        StringBuilder result = new StringBuilder();

        for (Resource content : contents) {
            String html = new String(content.getData());

            Document document = Jsoup.parse(html);
            result.append(document.body().text() + "\n");
        }

        return result.toString();
    }

    private void printPerMediaType(Collection<Resource> contents) {
        Multiset<MediaType> result = HashMultiset.create();

        for (Resource content : contents) {
            result.add(content.getMediaType());
        }

        Set<Multiset.Entry<MediaType>> entries = result.entrySet();
        for (Multiset.Entry<MediaType> entry : entries) {
            System.out.println("  " + entry.getElement().getName() + " = " + entry.getCount());
        }
    }
}
