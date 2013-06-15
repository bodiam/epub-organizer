package nl.jworks.epub.logic.strategy.isbn10;

import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.util.BookBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MetaDataIsbn10ScoreStrategyTest {

    @Test
    public void testIsbn() {
        MetaDataIsbn10ScoreStrategy strategy = new MetaDataIsbn10ScoreStrategy();

        BookImportContext importContext = new BookBuilder().isbn("1234567890").buildContext();

        Isbn10Score score = strategy.score(importContext);

        assertThat(score.getValue(), is("1234567890"));
        assertThat(score.getScore(), is(1.0));
    }

}
