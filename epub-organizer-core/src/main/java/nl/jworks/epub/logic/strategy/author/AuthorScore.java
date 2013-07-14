package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.Score;

import java.util.List;

/**
 * Simple scoring algorithm.
 */
public class AuthorScore extends Score<List<Author>> {

    public AuthorScore(List<Author> value, Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        List<Author> authors = getValue();

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
