package nl.jworks.epub.logic.strategy.title;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class TitleScorer extends AbstractScorer<TitleScore> {

    @Override
    public List<? extends ScoreStrategy<TitleScore>> getStrategies() {
        return Arrays.asList(
                new FileNameTitleScoreStrategy(),
                new MetaDataTitleScoreStrategy()
        );
    }

}
