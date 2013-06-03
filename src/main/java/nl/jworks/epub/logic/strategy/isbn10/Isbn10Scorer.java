package nl.jworks.epub.logic.strategy.isbn10;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class Isbn10Scorer extends AbstractScorer<Isbn10Score> {

    @Override
    public List<? extends ScoreStrategy<Isbn10Score>> getStrategies() {
        return Arrays.asList(new MetaDataIsbn10ScoreStrategy());
    }
}
