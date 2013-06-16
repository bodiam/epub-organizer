package nl.jworks.epub.logic.strategy.cover;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class CoverScorer extends AbstractScorer<CoverScore> {

    @Override
    public List<? extends ScoreStrategy<CoverScore>> getStrategies() {
        return Arrays.asList(new MetaDataCoverScoreStrategy());
    }
}
