package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.names.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extracts the Author from a directory name and calculates a score for the result.
 * <p/>
 * For example, it will translate the following:
 * <p/>
 * Christopher, Matt => Matt Christopher
 */
public class ParentDirectoryAuthorScoreStrategy implements AuthorScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(ParentDirectoryAuthorScoreStrategy.class);

    @Override
    public AuthorScore score(File source) {
        try {
            String dirName = source.getParentFile().getName();
            String[] rawTokens = splitAuthorPairTokens(dirName);
            String[] tokens = trim(rawTokens);

            List<Author> authors = new ArrayList<>();

            for (String token : tokens) {
                String[] rawNameTokens = splitAuthorNameTokens(token);
                String[] nameTokens = trim(rawNameTokens);

                if (nameTokens.length == 2) {
                    Name name = new PersonNameCategorizer().categorize(nameTokens);
                    Author author = new Author(name.getFirstName(), name.getLastName());

                    authors.add(author);
                }
            }

            return new AuthorScore(authors, ParentDirectoryAuthorScoreStrategy.class);
        } catch (Exception e) {
            log.error("Could not determine score for {}", source);

            return new AuthorScore(Collections.<Author>emptyList(), ParentDirectoryAuthorScoreStrategy.class);
        }
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

