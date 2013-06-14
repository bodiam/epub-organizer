package nl.jworks.epub.reader;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import org.junit.Ignore;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Set;

public class BookMetaDataReaderTest {


    @Ignore
    public void readMetaData() throws Exception {

//        File file = new File("/Users/erikp/Desktop/gutenberg/snowy.arsc.alaska.edu/gutenberg/cache/generated/107/pg107-images.epub");
//        File file = new File("/Users/erikp/Desktop/gutenberg/snowy.arsc.alaska.edu/gutenberg/cache/generated/42690/pg42690-images.epub");
        File file = new File("/Users/erikp/Desktop/gutenberg/snowy.arsc.alaska.edu/gutenberg/cache/generated/42631/pg42631-images.epub");

        EpubReader epubReader = new EpubReader();
        Book book = epubReader.readEpub(new FileInputStream(file));
        Metadata metadata = book.getMetadata();

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
//        System.out.println("Content             : " + book.getContents());

        printPerMediaType(book.getContents());
    }

    private void printPerMediaType(List<Resource> contents) {
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
