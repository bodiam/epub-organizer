package nl.jworks.epub.logic.strategy.publicationdate;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class PublicationDateScorer extends AbstractScorer<PublicationDateScore> {


    @Override
    public List<? extends ScoreStrategy<PublicationDateScore>> getStrategies() {
        return Arrays.asList(new PublicationDateMetaDataScoreStrategy());
    }
}
