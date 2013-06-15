package nl.jworks.epub.logic.strategy.isbn13;

import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.util.BookBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MetaDataIsbn13ScoreStrategyTest {

    @Test
    public void testIsbn() {
        MetaDataIsbn13ScoreStrategy strategy = new MetaDataIsbn13ScoreStrategy();

        BookImportContext importContext = new BookBuilder().isbn("1234567890123").buildContext();

        Isbn13Score score = strategy.score(importContext);

        assertThat(score.getValue(), is("1234567890123"));
        assertThat(score.getScore(), is(1.0));
    }
}
