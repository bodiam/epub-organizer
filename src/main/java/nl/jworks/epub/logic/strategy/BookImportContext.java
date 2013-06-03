package nl.jworks.epub.logic.strategy;

import com.google.common.base.Objects;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BookImportContext {

    private File file;
    private Book epubBook;

    public BookImportContext(File file) {
        initialize(file);
    }

    private void initialize(File file) {
        try {
            EpubReader epubReader = new EpubReader();
            this.epubBook = epubReader.readEpub(new FileInputStream(file));
            this.file = file;
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize using file " + file, e);
        }
    }

    public Book getEpubBook() {
        return epubBook;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("epubBook", epubBook)
                .add("file", file)
                .toString();
    }
}
