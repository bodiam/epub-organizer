package nl.jworks.epub.logic.strategy.author;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.names.Name;
import nl.jworks.epub.logic.names.PersonNameCategorizer;
import nl.siegmann.epublib.epub.EpubReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MetaDataAuthorScoreStrategy implements AuthorScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(MetaDataAuthorScoreStrategy.class);

    @Override
    public AuthorScore score(File source) {
        try {
            // read epub file
            EpubReader epubReader = new EpubReader();
            nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(source));

            List<nl.siegmann.epublib.domain.Author> epubAuthors = epubBook.getMetadata().getAuthors();

            AuthorScore categorizerScore = getAuthorScoreUsingCategorizer(epubAuthors);
            AuthorScore metaDataScore = getAuthorScoreUsingPlainMetaData(epubAuthors);

            Ordering<AuthorScore> o = new Ordering<AuthorScore>() {
                @Override
                public int compare(AuthorScore left, AuthorScore right) {
                    return Doubles.compare(left.getScore(), right.getScore());
                }
            };

            return o.max(Arrays.asList(categorizerScore, metaDataScore));
        } catch (Exception e) {
            log.error("Could not determine score for {}", source);

            return new AuthorScore(Collections.<Author>emptyList(), MetaDataAuthorScoreStrategy.class);
        }
    }

    private AuthorScore getAuthorScoreUsingCategorizer(List<nl.siegmann.epublib.domain.Author> epubAuthors) {
        List<Author> authors = new ArrayList<>();

        for (nl.siegmann.epublib.domain.Author epubAuthor : epubAuthors) {
            Name name = new PersonNameCategorizer().categorize(new String[]{epubAuthor.getFirstname(), epubAuthor.getLastname()});

            authors.add(new Author(name.getFirstName(), name.getLastName()));
        }
        return new AuthorScore(authors, MetaDataAuthorScoreStrategy.class);
    }

    private AuthorScore getAuthorScoreUsingPlainMetaData(List<nl.siegmann.epublib.domain.Author> epubAuthors) {
        List<Author> authors = new ArrayList<>();

        for (nl.siegmann.epublib.domain.Author epubAuthor : epubAuthors) {

            authors.add(new Author(epubAuthor.getFirstname(), epubAuthor.getLastname()));
        }
        return new AuthorScore(authors, MetaDataAuthorScoreStrategy.class);
    }
}
