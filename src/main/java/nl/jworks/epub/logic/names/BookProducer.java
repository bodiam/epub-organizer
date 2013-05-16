package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BookProducer {

    public Book produce(File input) throws IOException {

        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book book = epubReader.readEpub(new FileInputStream(input));

        return null;
    }
}
