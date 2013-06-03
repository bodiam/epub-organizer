package nl.jworks.epub.util;

import nl.jworks.epub.logic.strategy.BookImportContext;

import java.io.File;

public class DummyBookImportContext extends BookImportContext {

    public DummyBookImportContext() {
        super(new File("src/test/resources/epubs/dummycontext/dummy.epub"));
    }

}
