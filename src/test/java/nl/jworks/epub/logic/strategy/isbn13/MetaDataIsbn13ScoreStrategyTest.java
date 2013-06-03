package nl.jworks.epub.logic.strategy.isbn13;

import nl.jworks.epub.util.DummyBookContext;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MetaDataIsbn13ScoreStrategyTest {

    @Test
    public void testIsbn() {
        MetaDataIsbn13ScoreStrategy strategy = new MetaDataIsbn13ScoreStrategy();

        Isbn13Score score = strategy.score(new DummyBookContext());

        assertEquals("9781904902843", score.getValue());
        assertEquals(0.5, score.getScore());
    }
}
