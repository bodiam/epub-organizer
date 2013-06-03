package nl.jworks.epub.logic.strategy.isbn13;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class Isbn13Scorer extends AbstractScorer<Isbn13Score> {

    @Override
    public List<? extends ScoreStrategy<Isbn13Score>> getStrategies() {
        return Arrays.asList(new MetaDataIsbn13ScoreStrategy());
    }
}
