package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;

import java.util.List;

/**
 * Simple scoring algorithm.
 */
public class AuthorScore extends Score<List<Author>>{

    public AuthorScore(List<Author> source) {
        super(source);
    }

    @Override
    public double getValue() {
        List<Author> authors = getSource();

        if(authors.isEmpty()) {
            return 0;
        }

        Author author = authors.get(0);

        if(author.getFirstName() != null && author.getLastName() != null) {
            return 1.0;
        } else if(author.getLastName() != null) {
            return 0.75;
        } else if(author.getFirstName() != null) {
            return 0.5;
        } else {
            return 0;
        }
    }
}
