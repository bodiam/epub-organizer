package nl.jworks.epub.logic.strategy.tags;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class TagsScorer extends AbstractScorer<TagsScore> {

    @Override
    public List<? extends ScoreStrategy<TagsScore>> getStrategies() {
        return Arrays.asList(new TagsMetaDataScoreStrategy());
    }
}
