package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Extracts the Author from a filename and calculates a score for the result.
 *
 * For example, it will translate the following:
 *
 * Abbott, Megan - Dare Me.epub => Megan Abbott
 */
public class FileNameAuthorExtractor extends FileExtractSupport implements AuthorExtractor {

    public static final String SEPARATOR = "-";

    @Override
    public Score<List<Author>> scoreAuthors(File source) {

        String fileName = source.getName();

        if (fileName.contains(SEPARATOR)) {
            Author author = extractAuthorFromFileName(fileName);

            return new AuthorScore(Arrays.asList(author));
        } else {
            throw new IllegalArgumentException("Format in " + fileName + " not understood, doesn't contain separator");
        }
    }
}
