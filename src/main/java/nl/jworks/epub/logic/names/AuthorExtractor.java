package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;

import java.io.File;
import java.util.List;

public interface AuthorExtractor {

    /**
     * Extracts the author from the source file
     *
     * @param source The source file
     * @return A list of Authors
     */
    Score<List<Author>> scoreAuthors(File source);
}
