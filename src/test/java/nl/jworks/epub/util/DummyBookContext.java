package nl.jworks.epub.util;

import nl.jworks.epub.logic.strategy.BookContext;

import java.io.File;

public class DummyBookContext extends BookContext {

    public DummyBookContext() {
        super(new File("src/test/resources/epubs/dummycontext/dummy.epub"));
    }

}
