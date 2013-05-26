package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.names.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 * Extracts the Author from a filename and calculates a score for the result.
 * <p/>
 * For example, it will translate the following:
 * <p/>
 * Abbott, Megan - Dare Me.epub => Megan Abbott
 */
public class FileNameAuthorScoreStrategy implements AuthorScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(FileNameAuthorScoreStrategy.class);

    public static final String SEPARATOR = "-";
    public static final Pattern NAME_PATTERN = Pattern.compile("([a-zA-Z\\.]+)");

    @Override
    public AuthorScore score(File source) {

        return new AuthorScore(getAuthors(source), FileNameAuthorScoreStrategy.class);
    }

    private List<Author> getAuthors(File source) {
        try {
            String fileName = source.getName();

            Author author = extractAuthorFromFileName(fileName);

            return Arrays.asList(author);
        } catch (Exception e) {
            log.error("Could not determine score for {}", source);
            return Collections.emptyList();
        }
    }

    private Author extractAuthorFromFileName(String fileName) {
        Assert.isTrue(fileName.contains(SEPARATOR), "Format in " + fileName + " not understood, doesn't contain separator");

        String authorIncludingSpecialCharacters = extractAuthorPart(fileName);
        String[] tokens = splitFirstnameAndLastname(authorIncludingSpecialCharacters);

        Name name = new PersonNameCategorizer().categorize(tokens);

        return new Author(name.getFirstName(), name.getLastName());
    }

    private String extractAuthorPart(String fileName) {
        return substring(fileName, 0, indexOf(fileName, SEPARATOR));
    }

    private String[] splitFirstnameAndLastname(String input) {

        List<String> tokens = tokenizeName(input);

        String longest = tokens.get(0);

        List<String> rest = new ArrayList<>();

        for (int i = 1; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.length() > longest.length()) {
                rest.add(longest);
                longest = token;
            } else {
                rest.add(token);
            }
        }

        Collections.reverse(rest);

        return new String[]{
                longest, StringUtils.join(rest, " ")
        };
    }

    private List<String> tokenizeName(String input) {

        Matcher matcher = NAME_PATTERN.matcher(input);

        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
