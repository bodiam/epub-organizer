package nl.jworks.epub.logic.strategy.summary;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class SummaryScorer extends AbstractScorer<SummaryScore> {

    @Override
    public List<? extends ScoreStrategy<SummaryScore>> getStrategies() {
        return Arrays.asList(new SummaryMetaDataScoreStrategy());
    }
}
