package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.names.Name;
import nl.jworks.epub.logic.names.PersonNameCategorizer;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MetaDataAuthorScoreStrategy implements AuthorScoreStrategy {

    @Override
    public AuthorScore score(File source) {
        try {
            // read epub file
            EpubReader epubReader = new EpubReader();
            nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(source));

            // print the first title
            List<nl.siegmann.epublib.domain.Author> epubAuthors = epubBook.getMetadata().getAuthors();

            List<Author> authors = new ArrayList<>();

            for (nl.siegmann.epublib.domain.Author epubAuthor : epubAuthors) {
                Name name = new PersonNameCategorizer().categorize(new String[]{epubAuthor.getFirstname(), epubAuthor.getLastname()});

                authors.add(new Author(name.getFirstName(), name.getLastName()));
            }

            return new AuthorScore(authors, MetaDataAuthorScoreStrategy.class);

        } catch (Exception e) {
            throw new RuntimeException("Could not get author score for " + source, e);
        }
    }
}
