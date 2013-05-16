package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts the Author from a directory name and calculates a score for the result.
 *
 * For example, it will translate the following:
 *
 * Christopher, Matt => Matt Christopher
 */
public class ParentDirectoryAuthorExtractor extends FileExtractSupport implements AuthorExtractor {

    @Override
    public Score<List<Author>> scoreAuthors(File source) {

        String dirName = source.getParentFile().getName();
        String[] rawTokens = splitAuthorPairTokens(dirName);
        String[] tokens = trim(rawTokens);

        List<Author> authors = new ArrayList<>();

        for (String token : tokens) {
            String[] rawNameTokens = splitAuthorNameTokens(token);
            String[] nameTokens = trim(rawNameTokens);
            Name name = new PersonNameCategorizer().categorize(nameTokens);
            Author author = new Author(name.getFirstName(), name.getLastName());

            authors.add(author);
        }

        return new AuthorScore(authors);
    }

    private String[] trim(String[] input) {
        String[] trimmedArray = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            trimmedArray[i] = input[i].trim();
        }
        return trimmedArray;
    }

    private String[] splitAuthorNameTokens(String dirName) {
        return dirName.split(",");
    }

    private String[] splitAuthorPairTokens(String dirName) {
        return dirName.split("&");
    }
}

