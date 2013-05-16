package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AuthorScoreTest {

    @Test
    public void testFullAuthorScore() throws Exception {
        AuthorScore score = build(new Author("Erik", "Pragt"));

        assertThat(score.getValue(), is(1.0));
    }

    @Test
    public void testInitialsAuthorScore() throws Exception {
        // todo: should be lower than full (0.8, 0.9?)
        AuthorScore score = build(new Author("E.", "Pragt"));

        assertThat(score.getValue(), is(1.0));
    }

    @Test
    public void testLastNameAuthorScore() throws Exception {
        AuthorScore score = build(new Author(null, "Pragt"));

        assertThat(score.getValue(), is(0.75));
    }

    @Test
    public void testFirstNameAuthorScore() throws Exception {
        AuthorScore score = build(new Author("Erik", null));

        assertThat(score.getValue(), is(0.5));
    }

    private AuthorScore build(Author author) {
        return new AuthorScore(Arrays.asList(author));
    }
}
