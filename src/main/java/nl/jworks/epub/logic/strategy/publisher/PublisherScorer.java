package nl.jworks.epub.logic.strategy.publisher;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class PublisherScorer extends AbstractScorer<PublisherScore> {

    @Override
    public List<? extends ScoreStrategy<PublisherScore>> getStrategies() {
        return Arrays.asList(new PublisherMetaDataScoreStrategy());
    }
}
