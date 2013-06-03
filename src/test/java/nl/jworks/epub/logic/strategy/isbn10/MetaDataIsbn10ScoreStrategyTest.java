package nl.jworks.epub.logic.strategy.isbn10;

import nl.jworks.epub.util.DummyBookContext;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MetaDataIsbn10ScoreStrategyTest {

    @Test
    public void testIsbn() {
        MetaDataIsbn10ScoreStrategy strategy = new MetaDataIsbn10ScoreStrategy();

        Isbn10Score score = strategy.score(new DummyBookContext());

        assertEquals(null, score.getValue());
        assertEquals(0.0, score.getScore());
    }

}
